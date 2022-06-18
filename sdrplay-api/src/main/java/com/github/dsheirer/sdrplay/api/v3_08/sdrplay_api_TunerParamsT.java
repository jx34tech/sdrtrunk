// Generated by jextract

package com.github.dsheirer.sdrplay.api.v3_08;

import java.lang.foreign.GroupLayout;
import java.lang.foreign.MemoryAddress;
import java.lang.foreign.MemoryLayout;
import java.lang.foreign.MemorySegment;
import java.lang.foreign.MemorySession;
import java.lang.foreign.SegmentAllocator;
import java.lang.invoke.VarHandle;
public class sdrplay_api_TunerParamsT {

    static final  GroupLayout $struct$LAYOUT = MemoryLayout.structLayout(
        Constants$root.C_LONG$LAYOUT.withName("bwType"),
        Constants$root.C_LONG$LAYOUT.withName("ifType"),
        Constants$root.C_LONG$LAYOUT.withName("loMode"),
        MemoryLayout.structLayout(
            Constants$root.C_LONG$LAYOUT.withName("gRdB"),
            Constants$root.C_CHAR$LAYOUT.withName("LNAstate"),
            Constants$root.C_CHAR$LAYOUT.withName("syncUpdate"),
            MemoryLayout.paddingLayout(16),
            Constants$root.C_LONG$LAYOUT.withName("minGr"),
            MemoryLayout.structLayout(
                Constants$root.C_FLOAT$LAYOUT.withName("curr"),
                Constants$root.C_FLOAT$LAYOUT.withName("max"),
                Constants$root.C_FLOAT$LAYOUT.withName("min")
            ).withName("gainVals")
        ).withName("gain"),
        MemoryLayout.paddingLayout(32),
        MemoryLayout.structLayout(
            Constants$root.C_DOUBLE$LAYOUT.withName("rfHz"),
            Constants$root.C_CHAR$LAYOUT.withName("syncUpdate"),
            MemoryLayout.paddingLayout(56)
        ).withName("rfFreq"),
        MemoryLayout.structLayout(
            Constants$root.C_CHAR$LAYOUT.withName("dcCal"),
            Constants$root.C_CHAR$LAYOUT.withName("speedUp"),
            MemoryLayout.paddingLayout(16),
            Constants$root.C_LONG$LAYOUT.withName("trackTime"),
            Constants$root.C_LONG$LAYOUT.withName("refreshRateTime")
        ).withName("dcOffsetTuner"),
        MemoryLayout.paddingLayout(32)
    );
    public static MemoryLayout $LAYOUT() {
        return sdrplay_api_TunerParamsT.$struct$LAYOUT;
    }
    static final VarHandle bwType$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("bwType"));
    public static VarHandle bwType$VH() {
        return sdrplay_api_TunerParamsT.bwType$VH;
    }
    public static int bwType$get(MemorySegment seg) {
        return (int)sdrplay_api_TunerParamsT.bwType$VH.get(seg);
    }
    public static void bwType$set( MemorySegment seg, int x) {
        sdrplay_api_TunerParamsT.bwType$VH.set(seg, x);
    }
    public static int bwType$get(MemorySegment seg, long index) {
        return (int)sdrplay_api_TunerParamsT.bwType$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void bwType$set(MemorySegment seg, long index, int x) {
        sdrplay_api_TunerParamsT.bwType$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle ifType$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("ifType"));
    public static VarHandle ifType$VH() {
        return sdrplay_api_TunerParamsT.ifType$VH;
    }
    public static int ifType$get(MemorySegment seg) {
        return (int)sdrplay_api_TunerParamsT.ifType$VH.get(seg);
    }
    public static void ifType$set( MemorySegment seg, int x) {
        sdrplay_api_TunerParamsT.ifType$VH.set(seg, x);
    }
    public static int ifType$get(MemorySegment seg, long index) {
        return (int)sdrplay_api_TunerParamsT.ifType$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void ifType$set(MemorySegment seg, long index, int x) {
        sdrplay_api_TunerParamsT.ifType$VH.set(seg.asSlice(index*sizeof()), x);
    }
    static final VarHandle loMode$VH = $struct$LAYOUT.varHandle(MemoryLayout.PathElement.groupElement("loMode"));
    public static VarHandle loMode$VH() {
        return sdrplay_api_TunerParamsT.loMode$VH;
    }
    public static int loMode$get(MemorySegment seg) {
        return (int)sdrplay_api_TunerParamsT.loMode$VH.get(seg);
    }
    public static void loMode$set( MemorySegment seg, int x) {
        sdrplay_api_TunerParamsT.loMode$VH.set(seg, x);
    }
    public static int loMode$get(MemorySegment seg, long index) {
        return (int)sdrplay_api_TunerParamsT.loMode$VH.get(seg.asSlice(index*sizeof()));
    }
    public static void loMode$set(MemorySegment seg, long index, int x) {
        sdrplay_api_TunerParamsT.loMode$VH.set(seg.asSlice(index*sizeof()), x);
    }
    public static MemorySegment gain$slice(MemorySegment seg) {
        return seg.asSlice(12, 24);
    }
    public static MemorySegment rfFreq$slice(MemorySegment seg) {
        return seg.asSlice(40, 16);
    }
    public static MemorySegment dcOffsetTuner$slice(MemorySegment seg) {
        return seg.asSlice(56, 12);
    }
    public static long sizeof() { return $LAYOUT().byteSize(); }
    public static MemorySegment allocate(SegmentAllocator allocator) { return allocator.allocate($LAYOUT()); }
    public static MemorySegment allocateArray(int len, SegmentAllocator allocator) {
        return allocator.allocate(MemoryLayout.sequenceLayout(len, $LAYOUT()));
    }
    public static MemorySegment ofAddress(MemoryAddress addr, MemorySession session) { return RuntimeHelper.asArray(addr, $LAYOUT(), 1, session); }
}


