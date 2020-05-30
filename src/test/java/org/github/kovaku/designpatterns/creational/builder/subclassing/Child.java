package org.github.kovaku.designpatterns.creational.builder.subclassing;

public class Child extends Parent {

    private final String childName;

    private Child(ChildBuilder builder) {
        super(builder);
        this.childName = builder.childName;
    }

    public static ChildBuilder builder() {
        return new ChildBuilder();
    }

    public String getChildName() {
        return childName;
    }

    public static class ChildBuilder extends Parent.ParentBuilder<ChildBuilder> {

        private String childName;

        public ChildBuilder withChildName(String childName) {
            this.childName = childName;
            return this;
        }

        public Child build() {
            return new Child(this);
        }
    }
}
