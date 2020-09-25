package im.zego.expresssample.ui;

import android.content.Intent;
import android.content.pm.PackageManager;

import androidx.databinding.DataBindingUtil;

import android.os.Build;
import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.view.View;
import android.widget.TextView;


import com.zego.sound.processing.ui.SoundProcessingMainUI;

import im.zego.common.GetAppIDConfig;
import im.zego.common.util.PreferenceUtil;
import im.zego.datarecord.DataRecordActivity;
import im.zego.express.mixing.ui.ZGMixingDemoUI;
import im.zego.loginmultiroom.LoginMultiRoomActivity;
import im.zego.video.talk.ui.CoachVideoTalkUI;
import im.zego.video.talk.ui.CourseMenuUI;
import im.zego.video.talk.ui.SportShootUI;
import im.zego.video.talk.ui.SportVideoTalkUI;
import im.zego.video.talk.ui.ZGVideoTalkUI;

import im.zego.auxpublisher.ui.ZGAuxPublisherLoginUI;
import im.zego.custom.publish.ui.CustomCDNPublishActivity;
import im.zego.customrender.ui.ZGVideoRenderTypeUI;
import im.zego.expresssample.R;
import im.zego.expresssample.databinding.ActivityMainBinding;
import im.zego.expresssample.adapter.MainAdapter;
import im.zego.expresssample.entity.ModuleInfo;
import im.zego.common.ui.WebActivity;
import im.zego.im.ui.IMActivity;
import im.zego.mediaplayer.ui.MediaplayerMainActivity;
import im.zego.mixer.ui.MixerMainActivity;
import im.zego.play.ui.PlayActivityUI;
import im.zego.publish.ui.PublishActivityUI;
import im.zego.quickstart.ui.BasicCommunicationActivity;
import im.zego.soundlevelandspectrum.ui.SoundLevelAndSpectrumMainActivity;
import im.zego.videocapture.ui.ZGVideoCaptureOriginUI;

import static im.zego.common.util.PreferenceUtil.KEY_APP_ID;
import static im.zego.common.util.PreferenceUtil.KEY_APP_SIGN;


public class MainActivity extends AppCompatActivity {


    private MainAdapter mainAdapter = new MainAdapter();
    private static final int REQUEST_PERMISSION_CODE = 101;

    private ActivityMainBinding binding;

    private boolean isStudent = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (!isTaskRoot()) {
            /* If this is not the root activity */
            Intent intent = getIntent();
            String action = intent.getAction();
            if (intent.hasCategory(Intent.CATEGORY_LAUNCHER) && Intent.ACTION_MAIN.equals(action)) {
                finish();
                return;
            }
        }
        //
        if (PreferenceUtil.getInstance().getStringValue(KEY_APP_ID, "").equals("")) {
            PreferenceUtil.getInstance().setStringValue(KEY_APP_ID, String.valueOf(GetAppIDConfig.appID));
        }
        if (PreferenceUtil.getInstance().getStringValue(KEY_APP_SIGN, "").equals("")) {
            PreferenceUtil.getInstance().setStringValue(KEY_APP_SIGN, GetAppIDConfig.appSign);
        }
        //
        setTitle(getString(R.string.tx_title));
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        binding.codeDownload.setText(getString(R.string.code_download));
        binding.doc.setText(R.string.doc);
        binding.quickStart.setText(R.string.quick_start);
        binding.setting.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                SettingActivity.actionStart(MainActivity.this);
            }
        });

        // UI Setting
        binding.moduleList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.moduleList.setAdapter(mainAdapter);
        binding.moduleList.setItemAnimator(new DefaultItemAnimator());

        //是否安装到电视上,到电视直接进入学员观察端
        if (isStudent){
            SportVideoTalkUI.actionStart(MainActivity.this);
        }
    }


    public void jumpSourceCodeDownload(View view) {
//        WebActivity.actionStart(this, "https://github.com/zegoim/zego-express-example-topics-android", ((TextView) view).getText().toString());
        SportVideoTalkUI.actionStart(MainActivity.this);
    }

    public void jumpQuickStart(View view) {
//        WebActivity.actionStart(this, "https://doc-zh.zego.im/zh/195.html", ((TextView) view).getText().toString());
        CoachVideoTalkUI.actionStart(MainActivity.this);
    }

    public void jumpDoc(View view) {
        SportShootUI.actionStart(MainActivity.this);
//        WebActivity.actionStart(this, "https://doc-zh.zego.im/zh/693.html", ((TextView) view).getText().toString());

    }

    // 需要申请 麦克风权限-读写sd卡权限-摄像头权限
    private static String[] PERMISSIONS_STORAGE = {
            "android.permission.CAMERA",
            "android.permission.RECORD_AUDIO"};

    /**
     * 校验并请求权限
     */
    public boolean checkOrRequestPermission(int code) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, "android.permission.CAMERA") != PackageManager.PERMISSION_GRANTED
                    || ContextCompat.checkSelfPermission(this, "android.permission.RECORD_AUDIO") != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(PERMISSIONS_STORAGE, code);
                return false;
            }
        }
        return true;
    }
}
