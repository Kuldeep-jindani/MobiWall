package mobiwall.entwickler.pro.com.mobiwall;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;

public class Setting_Fragment extends Fragment {

    View view;
Switch notification_switch;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view=inflater.inflate(R.layout.setting_fragment,container,false);
        notification_switch=view.findViewById(R.id.notification_switch);
        final SharedPreferences notificationPref=context.getSharedPreferences("notificationpref", Context.MODE_PRIVATE);

        if (notificationPref.getString("notification","").equals("yes"))
        notification_switch.setChecked(true);
        else notification_switch.setChecked(false);

        notification_switch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {


                SharedPreferences.Editor editor=notificationPref.edit();

                if (isChecked)
                editor.putString("notification","yes").apply();
                else editor.putString("notification","no").apply();

            }
        });



        return view;
    }

    Context context;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        this.context=context;
    }
}
