package ru.mirea.chistyakov.mireaproject.ui.options;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import ru.mirea.chistyakov.mireaproject.R;

import static ru.mirea.chistyakov.mireaproject.AppPreference.*;
import static ru.mirea.chistyakov.mireaproject.AppPreference.Preferences.*;
import static java.lang.Integer.parseInt;


public class FragmentOptions extends Fragment {
    private EditText editText_browser_search;
    private Spinner spinner_music;
    private Button button_save_options;
    private Button button_default_options;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View root = inflater.inflate(R.layout.fragment_options, container, false);
        editText_browser_search = (EditText) root.findViewById(R.id.editText_browser_search);
        button_save_options = (Button) root.findViewById(R.id.button_save_options);
        button_default_options = (Button) root.findViewById(R.id.button_default_options);
        spinner_music = (Spinner) root.findViewById(R.id.spinner_music);

        button_save_options.setOnClickListener(v -> SaveOptions());
        button_default_options.setOnClickListener(v -> SetDefaultOptions());

        updateFields();
        Toast.makeText(getActivity(), "OPTIONS", Toast.LENGTH_SHORT).show();
        return root;
    }

    private void SaveOptions() {
        String  data = editText_browser_search.getText().toString();
        setPreferenceString(BROWSER_SEARCH, (String) data);
        data = String.valueOf(spinner_music.getSelectedItemPosition());
        setPreferenceString(MUSIC_CHOICE, data);
        Toast.makeText(getActivity(), "SAVE", Toast.LENGTH_SHORT).show();
    }

    private void SetDefaultOptions() {
        for (Preferences pref : values()) {
            setPreferenceString(pref, getDefaultPreference(BROWSER_SEARCH));
        }
        updateFields();
        Toast.makeText(getActivity(), "DEFAULT", Toast.LENGTH_SHORT).show();
    }

    private void updateFields(){
        editText_browser_search.setText(getPreference(BROWSER_SEARCH));
        spinner_music.setSelection(parseInt(getPreference(MUSIC_CHOICE)));
    }
}
