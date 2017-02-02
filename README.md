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
