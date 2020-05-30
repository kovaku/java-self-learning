package org.github.kovaku.designpatterns.creational.builder.subclassing;

public class Parent {

    private final String name;

    protected Parent(ParentBuilder<?> builder) {
        this.name = builder.name;
    }

    public String getName() {
        return name;
    }

    protected static class ParentBuilder<T extends ParentBuilder<T>> {

        private String name;

        public T withName(String name) {
            this.name = name;
            return (T) this;
        }

        private Parent build() {
            return new Parent(this);
        }
    }
}
