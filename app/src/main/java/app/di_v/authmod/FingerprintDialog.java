package app.di_v.authmod;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;


public class FingerprintDialog extends DialogFragment {

    private Callback listener;

    public interface Callback extends DialogInterface.OnClickListener {
        void onSuccess(String publicKey);
    }

    public static FingerprintDialog getInstance(@NonNull Callback listener) {
        FingerprintDialog dialog = new FingerprintDialog();
        dialog.listener = listener;
        return dialog;
    }


    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        // Get the layout inflater
        LayoutInflater inflater = getActivity().getLayoutInflater();

        // Inflate and set the layout for the dialog
        // Pass null as the parent view because its going in the dialog layout
        builder.setView(inflater.inflate(R.layout.dialog_fingerprint, null))
                .setTitle(R.string.auth)
                // action buttons
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Intent intent = new Intent(getContext(), MainActivity.class);
                        startActivity(intent);

                        FingerprintDialog.this.getDialog().cancel();

                        getActivity().finish();
                    }
                });
        return builder.create();
    }
}
