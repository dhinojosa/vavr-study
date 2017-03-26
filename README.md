#Java Slang Study

This is a study project for the Java Slang Presentation done by Daniel Hinojosa. 
This repository contains code examples that are compilable and testable.

Most if not all exercises are done as a JUnit Test.  This 
provides a fast, clean, and focused way to determine if the 
code actually works and so you can easily see the results.

##Java Slang Introduction

Java Slang is a library created by Daniel Dietrich, and Robert Winkler. 
This library builds a tighter bond between Java 8 and extra functional
programming ideas like Functional Data Structures, extra Functional Declarations,
Tuples, and other functional constructs like `Try`, a better functional `Option`, 
`Lazy` evaluation, `Either` evaluation, better `Future` handling, and `Validation`.
 
##Referential Transparency
A term often used in pure functional languages, where according to 
the Haskell language wiki (1) it is defined as n expression always evaluates to 
the same result in any context. That is when we invoke code that 
performs any work, that we get the same information back
any time that we invoke it. Consider the following example in a 
fake generic language, that I have concocted:

```$generic 
def add(x, y) {
   return x + y
}
```

By calling `add` above, every time I call this method or function, I get
the same response no matter how many times I call it, and there
are no _side effects_.  Side effects being where other operations
that are embedded in a function that you can verify and where an effect is changed.

Let's take the above generic example and 
lose our _referential transparency_.

```$generic
def add(x, y) {
   println("Adding two things!")
   return x + y
}
```

Now we have a side effect. This will now print 
something to the console and we have neither a verification of
a side effect occurring, and every time we run it there is something
extra happening. Some stringent developers will say that this is 
essentially "Lying to your users"

(1) https://wiki.haskell.org/Referential_transparency

## All Immutable, All The Time, Baby!

One of the things that makes programming so fun is immutability.  There is less
cognitive load to deal with when dealing with immutability.  If you no
longer have to query what state a
certain object or instance is in, neither do your processors have to query 
as well since they all maintain the same copy.

JavaSlang meets this immutable functional data structures, 
also known as _purely functional data structures_.

## Purely Functional Data Structures

Now that you know what these purely functional data structures are, lets start with 
the single linked list

### Single Linked List

A single linked list is an immutable collection just the way it sounds one single item, linked to 
another via a reference.

#### The Power of `prepend`

In functional programming, the power of an operation lies in prepend, and that is to 
add one element to the beginning of a `List`. Consider the following diagram.

First let's take a look at the code:

```$javaslang
List<Integer> original = List.of(1, 2, 3);
List<Integer> result = singleLinkedList.prepend(0);
```

Here is what `prepend` actually does in the source code inside of JavaSlang:

```$java
default List<T> prepend(T element) {
    //this refers to the List that this method is in
    return new List.Cons(element, this); 
}
```
That means that the diagram for this operation looks as follows:

```
original -> [1] -> [2] -> [3]
             ^
             |
result   -> [0]
```

There really isn't that much copying going on here so therefore the operation.
Where things get interesting is when we `append` rather than `prepend`. There of course will be cost, 
but let's take a look at how this is done using the Java Slang API, then take 
a look at the internals and see how it all works.

First, how it is simply done.
```$java
List<Integer> original = List.of(1, 2, 3);
List<Integer> result = original.append(4);
```
What is really going on inside.
```$java
default List<T> append(T element) {
   return (List)this.foldRight(of(element), (x, xs) -> {
       return xs.prepend(x);
   });
}
```

`foldRight` means that we start off with the item 
that we want to add, in our case `4`, and we build atop of that 
one item linearly until we have our new list, but we are going _right_ so
in our example we will start with 3 and go right.

```$java
Iteration 1:  x = 3 ; xs = [4]       ; result = [3, 4]
Iteration 2:  x = 2 ; xs = [3, 4]    ; result = [2, 3, 4]
Iteration 3:  x = 1 ; xs = [2, 3, 4] ; result = [1, 2, 3, 4]
```

If you really want to look at it as a test, 
 you can rewrite `append` for very own, only in this example, I am explicitly using `BiFunction` to show our lambda conversion.

```$java
List<Integer> original = List.of(1, 2, 3);
List<Integer> foldRight = original.foldRight(List.of(4), new BiFunction<Integer, List<Integer>, List<Integer>>() {
    @Override
    public List<Integer> apply(Integer integer, List<Integer> integers) {
        List<Integer> result = integers.prepend(integer);
        System.out.format("next: %2d; list: %-13s; result: %s\n", integer, integers, result);
        return result;
    }
});
```

Now here is how it looks when we convert the `BiFunction` into a lambda.

```$java
List<Integer> original = List.of(1, 2, 3);
List<Integer> foldRight = original.foldRight(List.of(4), (integer, integers) -> {
     List<Integer> result = integers.prepend(integer);
     System.out.format("next: %2d; list: %-13s; result: %s\n", integer, integers, result);
});
```
           
Where the results of the operation are, which matches what we thought

```$java
next:  3; list: List(4)      ; result: List(3, 4)
next:  2; list: List(3, 4)   ; result: List(2, 3, 4)
next:  1; list: List(2, 3, 4); result: List(1, 2, 3, 4)
```

## Queue

Queues, or what we Americans call "gettin' in line", is just that, a first in, first out semantics. Only this
time there are immutable and persistent considerations to be aware of. Consider the following code:

```$java
Queue<Integer> queue = Queue.of(1, 2, 3, 4);
Queue<Integer> newQueue = queue.enqueue(5,6,7,8);
```
Here `queue` contains 1,2,3,4 as expected, then we take `queue` and `enqueue` some further
numbers 5,6,7,8.  Because of the immutablity of the `queue` it holds onto the same numbers.
The functional trick in the backend is separation of the elements, because we add items doesn't
necessarily create a bunch of useless objects

The way `queue` works internally is that a `front` and `rear` reference is held internal to the
data structure.  When we enqueue `5,6,7,8` in the above example. `front` will contain `1,2,3,4` and 
`rear` will refer to `8,7,6,5`.  If we `enqueue` more then it will `prepend` (there it is again) to the
`rear`.  The magic happens when we construct a `Queue` again, by a process like `dequeue` where the `front` 
is depleted until empty. When a new `Queue` is created when `dequeue`is called and the `front` is empty 
while the `rear` is not. The new `Queue` will move the `rear` to the `front` and reverse the content and 
 leave the `front` empty.
 
            
## Performance Concerns

While performance concerns are always valid when immutable collections are concerned, the JavaSlang
 website contains a table of all O-notation expectations for different behaviors
  on different collections


http://www.javaslang.io/javaslang-docs/#_performance_characteristics


## Options

`Option` has been in the JDK for a while now in the form of `java.util.Optional<T>` and
while somewhat useful there is one important aspect about `Optional` and that is that it
isn't serializable.  That in turn also means that it is not meant to be used as member 
 variables.  Java Slang's `Option` is serializable, and there was a time where there was some 
 contemplation of removing it.
 
 Here is a simple example of what is available with JavaSlang's variety of `Option`.
 
```$java
Option<String> option1 = Option.none();
Option<String> option2 = Option.of("Foo");

option1.getOrElse("Nope, Sorry");  //Nope, Sorry
option2.getOrElse("Nope, Sorry");  //Foo
```

## Tuples
If you, our dearly beloved reader has never ventured past the lush lawns of Java,
 you may have not seen tuples. Tuple are immutable container for disparate objects. If you 
 ever wondered how you can return two items from a method, that would be a `Tuple`. In JavaSlang,
 there is `Tuple1`, `Tuple2`, `Tuple3`, `Tuple4`, `Tuple5`,  all the way to `Tuple8`. 
  
```$java
Tuple2<String, Integer> tuple2 = new Tuple2<>("Foo", 4);
tuple2._1()  // Foo
tuple2._2()  // 4
```

From the last example, if you might be wondering where did that `_1` and `_2` come from? 
Being that JavaSlang is inspired from Scala and Scala ultimately being inspired from Haskell,
this where the notion of first and second come into play.  Where are tuples used? Let's take a look 
at an immutable map.

```$java
Map<Integer, String> nums = HashMap.of(
                Tuple.of(1, "One"), 
                Tuple.of(2, "Two"), 
                Tuple.of(3, "Three"));
nums.get(1).getOrElse("Unknown");  //One
nums.get(10).getOrElse("Unknown"); //Unknown
```

#Functions

Functions are verbs.  They invoke a behavior and in Java 8 in
order to create a function, you need an `interface` with one 
`abstract` method.
Let's pick on `java.util.BiFunction` in JDK 8.

```$java
@FunctionalInterface
public interface BiFunction<T, U, R> {
    R apply(T t, U u);
}
```

Just because `BiFunction` has `@FunctionalInterface` doesn't make it a function. 
That is purely a call to the compiler to help us ensure that this indeed
is an `interface` with one `abstract` method.

Let's take a look though at what the JavaSlang alternative for `BiFunction` looks like.

```$java
@FunctionalInterface
public interface Function2<T1, T2, R> extends λ<R>, BiFunction<T1, T2, R>  {
  R apply(T1 t1, T2 t2);
}
```

Interesting find. `Function2` derives from `java.util.BiFunction`. Great! 
So anywhere something accepts a `BiFunction` you can fit a nice `Function2`.  It also extends 
from `λ` and you know what that means, so let's move on.  I am just kidding you don't 
know what `λ` does, what the hell is that thing? `λ` is a super interface!  The super `interface`
merely dictates that is you want to roll with `λ` gang, 
you need to be able to turn into a `curried` function, back to an uncurried function
called `tupled`, and you should be able to `memoize` a function which is to cache a set of
inputs. All part of the plan.

Before we begin to see all the goodness of a `Fuction` just note that just like `Tuple`, 
function come in these wide array of flavors, `Function1`, `Function2`, `Function3`, `Function4`, 
all the way to `Function8`. Now spring into action with JavaSlang's functions.

```java
Function0<LocalDate> function0 = new Function0<LocalDate>() {
    @Override
    public LocalDate apply() {
        return LocalDate.now();
    }
};
List<LocalDate> listOfLocalDates = List.fill(10, function0);
listOfLocalDates.forEach(new Consumer<LocalDate>() {
    @Override
    public void accept(LocalDate x) {
        System.out.println(x);
    }
});
```
So in the last example, I went mighty explicit with declaring what `Function0` is and `java.util.Consumer`.
Taking that all away, we are left with the following sweet treat.

```java
Function0<LocalDate> function0 = LocalDate::now;
List<LocalDate> listOfLocalDates = List.fill(10, function0);
listOfLocalDates.forEach(System.out::println);
```

Running the above lists 10 dates using `List.fill` a method on JavaSlang's `List`. Pretty nifty.

## Pattern Matching

Pattern Matching is just one of the features that I sorely miss with Java.  
The idea of Pattern Matching is more than just a switch statement which has it's problems.

```java
String str = "January";
int month;
switch (str) {
    case "January":
        month = 1;
        break;
    case "February":
        month = 2;
        break;
    case "March":
        month = 3;
        break;
    default:
        month = 4;
        break;
}
```

The above example, has a bunch of `break`s and that's pretty noisy. 
The other code smell is `int month` outside the block.  That
mean there is mutability right above the `switch` and that
is not cool.  We are favoring immutability, and it would be 
great to set `month` to the `switch` directly. The other 
problem is we are limited to what we can feed a `switch`; 
`String`, primitives, primitive wrappers, and `enum`. Weak sauce.
It would be great if we can bust the doors wide open and allow
more members and not only that if we can break down the 
contents to match what is inside these objects.  That's what 
pattern matching brings and is a _very_ powerful concept.

Using the last example, let us rewrite it, cleaner, with pattern matching.

```java
Integer month = Match("January").of(
   Case(is("January"), 1),
   Case(is("February"), 2),
   Case(is("March"), 3),
   Case(is("April"), 4),
   Case($(), 5));
```

Oh, damn, that looks great. Here we want to match `January`
and return the ordinal number matching the month.  That's Java!  
No, I am serious that's Java!  But wait, careful 
analysis by you, our dear reader notices `$()` and
you can probably piece together that 
this is some sort of default. That is true.  These
are called Atomic Patterns and yes it is a way to describe 
something that is either a "catch all", a default, 
or a predicate.

Here is an example where we are using it to dig into objects to find out what inside and
match on those items

```java
Option<String> middleName = Option.of("Lisa");
String message = Match(middleName).of(
     Case(Some($()), x -> "Middle name is " + x),
     Case(None(), "No middle name here"));
```

With this pattern match. I know there is an Option and it either
in type `Some` meaning it has something or `None`.  We can burrow
down and use `$()` to signify that we wish to match with
whatever is in the `Some`, you see after that, that we
have a function so we can actually match on the middle name.

## Conclusion
There is way much more goodness with JavaSlang.  Check out the 
project, or attend one of my sessions where we can discover 
some more of the exciting aspects of JavaSlang.  Once you delight
in some of these new features you'll either want to skip over
to another functional language like Scala or Clojure or you'll 
just want to demand more out of Java.  