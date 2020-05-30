package org.github.kovaku.nested;

public class InnerClasses {

    private String field = "Outer class!";

    private void staticNestedClassTest() {
        System.out.println(StaticNestedClass.staticNestedClass);
    }

    private void innerClassTest() {
        InnerClass innerClass = new InnerClass();
        innerClass.innerTest();
        System.out.println(innerClass.innerField);
    }

    private static class StaticNestedClass {

        private static String staticNestedClass = "Static nested constant";

        private void staticNestedTest() {
            //Not allowed to reference on static fields
            //field = "Set by the static nested class!";
        }
    }

    private class InnerClass {

        private String field = "Inner class";
        private final String innerField = "Public field of the inner class.";
        //Not allowed to have static fields
        //private static String staticInnerField;

        private void innerTest() {
            System.out.println(field);
        }

        private void shadowTest(String field) {
            System.out.println(field);
            System.out.println(this.field);
            System.out.println(InnerClasses.this.field);
        }
    }
}
