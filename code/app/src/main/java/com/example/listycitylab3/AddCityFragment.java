package com.example.listycitylab3;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class AddCityFragment extends DialogFragment {

    public interface AddCityDialogListener {
        void addCity(City city);
        void updateCity(int position, City updatedCity);
    }// Two modes, add city or update city

    private static final String ARG_CITY = "arg_city";
    private static final String ARG_POS  = "arg_pos"; // -1 when adding

    public static AddCityFragment newInstance(@Nullable City city, int position) {
        AddCityFragment f = new AddCityFragment();
        Bundle args = new Bundle();
        if (city != null) args.putSerializable(ARG_CITY, city);
        args.putInt(ARG_POS, position);
        f.setArguments(args);
        return f;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        View view = LayoutInflater.from(requireContext()).inflate(R.layout.fragment_add_city, null);
        EditText etCity = view.findViewById(R.id.edit_text_city_text);
        EditText etProv = view.findViewById(R.id.edit_text_province_text);

        City passed = null;
        int pos;
        Bundle args = getArguments();
        if (args != null) {
            passed = (City) args.getSerializable(ARG_CITY);
            pos = args.getInt(ARG_POS, -1);
        } else {
            pos = -1;
        }

        // Prefill if editing
        if (passed != null) {
            etCity.setText(passed.getName());
            etProv.setText(passed.getProvince());
        }

        AddCityDialogListener listener = (AddCityDialogListener) requireActivity();
        boolean editing = (pos >= 0);

        return new AlertDialog.Builder(requireContext())
                .setTitle(editing ? "Edit City" : "Add City")
                .setView(view)
                .setNegativeButton("Cancel", null)
                .setPositiveButton(editing ? "Save" : "Add", (d, w) -> {
                    String newName = etCity.getText().toString().trim();
                    String newProv = etProv.getText().toString().trim();
                    if (editing) {
                        // create an updated copy (or mutate a clone)
                        listener.updateCity(pos, new City(newName, newProv));
                    } else {listener.addCity(new City(newName, newProv));}}).create();
    }
}

