// Generated by jextract

package com.github.dsheirer.sdrplay.api.v3_08;

import java.lang.foreign.Addressable;
import java.lang.foreign.MemoryAddress;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.MemorySession;
public interface sdrplay_api_ReleaseDevice_t {

    int apply(java.lang.foreign.MemoryAddress device);
    static MemorySegment allocate(sdrplay_api_ReleaseDevice_t fi, MemorySession session) {
        return RuntimeHelper.upcallStub(sdrplay_api_ReleaseDevice_t.class, fi, constants$3.sdrplay_api_ReleaseDevice_t$FUNC, session);
    }
    static sdrplay_api_ReleaseDevice_t ofAddress(MemoryAddress addr, MemorySession session) {
        MemorySegment symbol = MemorySegment.ofAddress(addr, 0, session);
        return (java.lang.foreign.MemoryAddress _device) -> {
            try {
                return (int)constants$3.sdrplay_api_ReleaseDevice_t$MH.invokeExact((Addressable)symbol, (java.lang.foreign.Addressable)_device);
            } catch (Throwable ex$) {
                throw new AssertionError("should not reach here", ex$);
            }
        };
    }
}


