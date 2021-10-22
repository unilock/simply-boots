package io.github.alabasteralibi.simplyboots.components;

import dev.onyxstudios.cca.api.v3.component.Component;

public interface ClampedBootIntComponent extends Component {
    int getValue();
    void increment();
    void decrement();
}
