// Generated by jextract

package com.github.dsheirer.sdrplay.api.v3_07;

import java.lang.foreign.FunctionDescriptor;
import java.lang.invoke.MethodHandle;
class constants$4 {

    static final FunctionDescriptor sdrplay_api_DisableHeartbeat_t$FUNC = FunctionDescriptor.of(Constants$root.C_LONG$LAYOUT);
    static final MethodHandle sdrplay_api_DisableHeartbeat_t$MH = RuntimeHelper.downcallHandle(
        constants$4.sdrplay_api_DisableHeartbeat_t$FUNC
    );
    static final FunctionDescriptor sdrplay_api_DebugEnable_t$FUNC = FunctionDescriptor.of(Constants$root.C_LONG$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_LONG$LAYOUT
    );
    static final MethodHandle sdrplay_api_DebugEnable_t$MH = RuntimeHelper.downcallHandle(
        constants$4.sdrplay_api_DebugEnable_t$FUNC
    );
    static final FunctionDescriptor sdrplay_api_GetDeviceParams_t$FUNC = FunctionDescriptor.of(Constants$root.C_LONG$LAYOUT,
        Constants$root.C_POINTER$LAYOUT,
        Constants$root.C_POINTER$LAYOUT
    );
    static final MethodHandle sdrplay_api_GetDeviceParams_t$MH = RuntimeHelper.downcallHandle(
        constants$4.sdrplay_api_GetDeviceParams_t$FUNC
    );
}


