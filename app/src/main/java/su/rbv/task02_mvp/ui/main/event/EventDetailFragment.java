package su.rbv.task02_mvp.ui.main.event;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import su.rbv.task02_mvp.Const;
import su.rbv.task02_mvp.ui.main.IMainView;
import su.rbv.task02_mvp.R;
import su.rbv.task02_mvp.data.JSONEvent;

public class EventDetailFragment extends MvpAppCompatFragment implements IEventDetailView {

    private static final String LIKES = "+12345";
    private static final String EMAIL = "putin@kremlin.ru";
    private static final String SITE = "http://navalny.com";

    @InjectPresenter
    EventDetailPresenter eventDetailPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_event_detail, container, false);

        androidx.appcompat.widget.Toolbar toolbar = view.findViewById(R.id.event_detail_toolbar);
        toolbar.inflateMenu(R.menu.event_detail_menu);

        // if "back arrow" click in toolbar, close NewsFilterFragment without selected category saving
        view.findViewById(R.id.event_detail_back).setOnClickListener((v) -> close());

        Bundle bundle = getArguments();
        if (bundle != null) {

            JSONEvent event = (JSONEvent) bundle.getSerializable(Const.BUNDLE_EVENT);
            String strDate = bundle.getString(Const.BUNDLE_EVENT_STR_DATE);

            ((TextView) view.findViewById(R.id.event_detail_title)).setText(event.name);
            ((TextView) view.findViewById(R.id.event_detail_name)).setText(event.name);
            ((TextView) view.findViewById(R.id.event_detail_date)).setText(strDate);
            ((TextView) view.findViewById(R.id.event_detail_description)).setText(event.organisation);
            ((TextView) view.findViewById(R.id.event_detail_address)).setText(event.address);
            ((TextView) view.findViewById(R.id.event_detail_phone)).setText(event.phone);
            ((TextView) view.findViewById(R.id.event_detail_body)).setText(event.description);
            ((TextView) view.findViewById(R.id.event_detail_likes)).setText(LIKES);

            // handle "mail to" click - open mail app
            view.findViewById(R.id.event_detail_email).setOnClickListener((v) -> eventDetailPresenter.onSendMailClick(EMAIL));

            // handle "go to site" click - open browser
            view.findViewById(R.id.event_detail_site).setOnClickListener((v) -> eventDetailPresenter.onOpenSiteClick(SITE));

            view.setFocusableInTouchMode(true);
            view.requestFocus();
            view.setOnKeyListener((v, keyCode, keyEvent) -> {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    close();
                    return true;
                }
                return false;
            });

        }

        return view;
    }

    private void close() {
        ((IMainView) requireActivity()).closeEventDetailFragment();
    }

    @Override
    public void sendMail(String mailTo) {
        try {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                    Const.INTENT_EMAIL_SCHEME, mailTo, null));
            startActivity(Intent.createChooser(emailIntent, Const.INTENT_EMAIL_TITLE));
        } catch (Exception e) {
            Toast.makeText(getContext(), getResources().getString(R.string.event_detail_no_mail_app), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void openSite(String siteURL) {
        try {
            Intent intentSite = new Intent(Intent.ACTION_VIEW);
            intentSite.setData(Uri.parse(siteURL));
            startActivity(intentSite);
        } catch (Exception e) {
            Toast.makeText(getContext(), getResources().getString(R.string.event_detail_no_browser_app), Toast.LENGTH_SHORT).show();
        }
    }

}