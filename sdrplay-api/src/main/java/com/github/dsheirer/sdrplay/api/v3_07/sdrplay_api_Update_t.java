// Generated by jextract

package com.github.dsheirer.sdrplay.api.v3_07;

import java.lang.foreign.Addressable;
import java.lang.foreign.MemoryAddress;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.MemorySession;
public interface sdrplay_api_Update_t {

    int apply(java.lang.foreign.MemoryAddress dev, int tuner, int reasonForUpdate, int reasonForUpdateExt1);
    static MemorySegment allocate(sdrplay_api_Update_t fi, MemorySession session) {
        return RuntimeHelper.upcallStub(sdrplay_api_Update_t.class, fi, constants$5.sdrplay_api_Update_t$FUNC, session);
    }
    static sdrplay_api_Update_t ofAddress(MemoryAddress addr, MemorySession session) {
        MemorySegment symbol = MemorySegment.ofAddress(addr, 0, session);
        return (java.lang.foreign.MemoryAddress _dev, int _tuner, int _reasonForUpdate, int _reasonForUpdateExt1) -> {
            try {
                return (int)constants$5.sdrplay_api_Update_t$MH.invokeExact((Addressable)symbol, (java.lang.foreign.Addressable)_dev, _tuner, _reasonForUpdate, _reasonForUpdateExt1);
            } catch (Throwable ex$) {
                throw new AssertionError("should not reach here", ex$);
            }
        };
    }
}


