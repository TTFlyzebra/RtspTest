package com.longhorn.dvrexplorer.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.flyzebra.live555.rtsp.RtspVideoView;
import com.longhorn.dvrexplorer.R;
import com.longhorn.dvrexplorer.module.wifi.CommandType;
import com.longhorn.dvrexplorer.module.wifi.ResultData;
import com.longhorn.dvrexplorer.module.wifi.SocketResult;
import com.longhorn.dvrexplorer.module.wifi.SocketTools;
import com.longhorn.dvrexplorer.utils.ByteTools;
import com.longhorn.dvrexplorer.utils.FlyLog;

/**
 * Created by FlyZebra on 2018/5/17.
 * Descrip:
 */

public class SetFragment extends Fragment{
    public SetFragment(){
    }

    public static SetFragment newInstance() {
        Bundle args = new Bundle();
        SetFragment fragment = new SetFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){
        return inflater.inflate(R.layout.fragment_set,container,false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
    }

    @Override
    public void onStart() {
        super.onStart();
        SocketTools.getInstance().sendCommand(CommandType.GET_RECORD_CFG, new SocketResult() {
            @Override
            public void result(ResultData msg) {
                FlyLog.d("GET_RECORD_CFG length=%d,data=%s", msg.getMark(), ByteTools.bytes2HexString(msg.getBytes()));
                if(msg.getMark()!=15) return;
                byte[] ret = msg.getBytes();
//                if(ret[0]!=0xee||ret[1]!=0xaa||ret[6]!=0x11||ret[7]!=0x00) return;
                if(ret[8]==0x00){
                    //设置1080
                }
            }
        });
    }
}
