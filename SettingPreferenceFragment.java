package com.yujin.a20190809;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;

public class SettingPreferenceFragment extends PreferenceFragment {
    SharedPreferences pref;

    EditTextPreference input_url1;
    EditTextPreference input_url2;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.settings_preference);

        input_url1 = (EditTextPreference)findPreference("input_url1");
        input_url2 = (EditTextPreference)findPreference("input_url2");

        pref = PreferenceManager.getDefaultSharedPreferences(getActivity());

        if(!pref.getString("input_url1", "").equals("")) {
            String url1 = pref.getString("input_url1", "");
        }
        if(!pref.getString("input_url2", "").equals("")) {
            String url2 = pref.getString("input_url2", "");
        }

    }
}
