package com.starrocks.udf.example;

// SumInt: UDAF
public class SumInt {
    public static class State {
        int counter = 0;
        public int serializeLength() { return 4; }
    }

    // create: 初始化
    public State create() {
        return new State();
    }

    public void destroy(State state) {
    }

    // 合并数据
    public final void update(State state, Integer val) {
        if (val != null) {
            state.counter += val;
        }
    }

    // 数据序列化到 buffer
    public void serialize(State state, java.nio.ByteBuffer buff) {
        buff.putInt(state.counter);
    }

    // 合并数据（从 buffer）
    public void merge(State state, java.nio.ByteBuffer buffer) {
        int val = buffer.getInt();
        state.counter += val;
    }

    // 返回最终数据
    public Integer finalize(State state) {
        return state.counter;
    }
}

// SELECT SumInt(num) FROM `temp`.`test_int`;