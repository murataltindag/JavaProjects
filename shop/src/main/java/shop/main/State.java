package shop.main;

/*
 * The State interface is used to process input and output and return the next state.
 */
public interface State {
    void process(InputSource inputSource, OutputSource outputSource);
    State nextState();
} 
