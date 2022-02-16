package com.infosupport.demos.h9.generics;

import java.util.Collection;
import java.util.List;

public class Generics {

    public void doSomethingWithList(List list) {
        // but you don't know the element type and whether it's a raw list or typed list
        System.out.println(list);
    }

    public void doSomethingWithListInt1(Object value) {
        // not allowed to check with type parameter:
        // if (value instanceof List<Integer>) // ...

        // allowed
        if (value instanceof List) {
            // but you don’t get any information about the element type:
            // ...
        }
        // same
        if (value instanceof List<?>) {
            // ...
        }
    }

    public void doSomethingWithListInt2(Collection<Integer> c) {
        // this will not compile in Java
        // if(c instanceof List<Integer>){
        //  // ...
        // }
    }

    public void doSomethingWithListInt3(Collection<?> c) {
        // allowed, but <Integer> can't be checked at runtime, only whether it's a List; hence the warning
        List<Integer> intList = (List<Integer>) c; // runtime exception when it's not a List<Integer>
        System.out.println(intList.stream().mapToInt(i -> i).sum()); // runtime exception when it's not a List<Integer>
    }
}
