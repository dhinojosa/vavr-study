#Java Slang Study
WARNING: Still under development, things could be and will likely be incorrect.


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
            
## Performance Concerns