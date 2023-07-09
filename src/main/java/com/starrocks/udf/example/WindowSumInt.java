package com.starrocks.udf.example;

// WindowSumInt: UDWF
public class WindowSumInt {    
    public static class State {
        int counter = 0;
        public int serializeLength() { return 4; }
        @Override
        public String toString() {
            return "State{" +
                    "counter=" + counter +
                    '}';
        }
    }

    public State create() {
        return new State();
    }

    public void destroy(State state) {

    }

    public void update(State state, Integer val) {
        if (val != null) {
            state.counter+=val;
        }
    }

    public void serialize(State state, java.nio.ByteBuffer buff) {
        buff.putInt(state.counter);
    }

    public void merge(State state, java.nio.ByteBuffer buffer) {
        int val = buffer.getInt();
        state.counter += val;
    }

    public Integer finalize(State state) {
        return state.counter;
    }

    public void reset(State state) {
        state.counter = 0;
    }

    // windowUpdate: 窗口级别计算
    public void windowUpdate(State state,
                            int peer_group_start, int peer_group_end,
                            int frame_start, int frame_end,
                            Integer[] inputs) {
        for (int i = (int)frame_start; i < (int)frame_end; ++i) {
            state.counter += inputs[i];
        }
    }
}

/*
SELECT id, num, WindowSumInt(num) OVER (ORDER BY id ROWS BETWEEN UNBOUNDED PRECEDING AND CURRENT ROW)
FROM `temp`.`test_int`
ORDER BY id;
 */