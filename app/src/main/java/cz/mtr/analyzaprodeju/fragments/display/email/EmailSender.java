package cz.mtr.analyzaprodeju.fragments.display.email;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.widget.Toast;

import java.io.File;

import cz.mtr.analyzaprodeju.BuildConfig;
import cz.mtr.analyzaprodeju.models.Model;

public class EmailSender {

    public EmailSender() {

    }


    public void sendEmail(Context context, File attachment) {
        send(context, attachment);

    }

    private void send(Context context, File attachment) {
        final File file = attachment;
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("application/octet-stream");
        intent.putExtra(Intent.EXTRA_SUBJECT, file.getName());
        if (!file.exists() || !file.canRead()) {
            Toast.makeText(context, "Attachment Error", Toast.LENGTH_SHORT).show();
            return;
        }
        String AUTHORITY = BuildConfig.APPLICATION_ID + ".fileprovider";
        Uri uri = AttachmentFileProvider.getUriForFile(context, AUTHORITY, file);
        intent.putExtra(Intent.EXTRA_STREAM, uri);
        Model.getInstance().getMainActivityContext().startActivity(Intent.createChooser(intent, "Send email..."));
    }
}
