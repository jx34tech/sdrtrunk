// Generated by jextract

package com.github.dsheirer.sdrplay.api.v3_08;

import java.lang.foreign.GroupLayout;
import java.lang.foreign.MemoryAddress;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.MemorySession;
import java.lang.foreign.SegmentAllocator;
import java.lang.invoke.VarHandle;
public class sdrplay_api_RspDuoModeCbParamT {

    static final  GroupLayout $struct$LAYOUT = MemoryLayout.structLayout(
        Constants$root.C_LONG$LAYOUT.withName("modeChangeType")
    );
    public static MemoryLayout $LAYOUT() {
        return sdrplay_api_RspDuoModeCbParamT.$struct$LAYOUT;
    }
    static final VarHandle modeChangeType$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("modeChangeType"));
    public static VarHandle modeChangeType$VH() {
        return sdrplay_api_RspDuoModeCbParamT.modeChangeType$VH;
    }
    public static int modeChangeType$get(MemorySegment seg) {
        return (int)sdrplay_api_RspDuoModeCbParamT.modeChangeType$VH.get(seg);
    }
    public static void modeChangeType$set( MemorySegment seg, int x) {
        sdrplay_api_RspDuoModeCbParamT.modeChangeType$VH.set(seg, x);
    }
    public static int modeChangeType$get(MemorySegment seg, long index) {
        return (int)sdrplay_api_RspDuoModeCbParamT.modeChangeType$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void modeChangeType$set(MemorySegment seg, long index, int x) {
        sdrplay_api_RspDuoModeCbParamT.modeChangeType$VH.set(seg.asSlice(index*sizeof()), x);
    }
    public static long sizeof() { return $LAYOUT().byteSize(); }
    public static MemorySegment allocate(SegmentAllocator allocator) { return allocator.allocate($LAYOUT()); }
    public static MemorySegment allocateArray(int len, SegmentAllocator allocator) {
        return allocator.allocate(MemoryLayout.sequenceLayout(len, $LAYOUT()));
    }
    public static MemorySegment ofAddress(MemoryAddress addr, MemorySession session) { return RuntimeHelper.asArray(addr, $LAYOUT(), 1, session); }
}


