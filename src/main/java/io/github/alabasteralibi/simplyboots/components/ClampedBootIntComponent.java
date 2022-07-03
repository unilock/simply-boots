package io.github.alabasteralibi.simplyboots.components;

import dev.onyxstudios.cca.api.v3.component.Component;

public interface ClampedBootIntComponent extends Component {
    /**
     * Gets this component's value.
     * @return An integer representing the current value of the component.
     */
    int getValue();

    /**
     * Increments the value of this component by one. Will not go over the component's maximum value.
     */
    void increment();

    /**
     * Decrements the value of this component by one. Will not go under the component's minimum value.
     */
    void decrement();
}
