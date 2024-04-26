package shop.main;

public interface State {
    void process(InputSource inputSource, OutputSource outputSource);
    State nextState();
} 
