package su.rbv.task02_mvp.ui.main.help;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import moxy.MvpAppCompatFragment;
import moxy.presenter.InjectPresenter;
import moxy.presenter.ProvidePresenter;
import su.rbv.task02_mvp.App;
import su.rbv.task02_mvp.Const;
import su.rbv.task02_mvp.R;
import su.rbv.task02_mvp.data.JSONCategory;


public class HelpFragment extends MvpAppCompatFragment implements IHelpView {

    private final static int listColumns = 2;

    private View progressBar;
    private RecyclerView listHelp;

    @Inject
    @InjectPresenter
    HelpPresenter helpPresenter;

    @ProvidePresenter
    HelpPresenter providePresenter() {
        App.getComponent().inject(this);
        return helpPresenter;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_help, container, false);

        progressBar = view.findViewById(R.id.help_progressbar);
        listHelp = view.findViewById(R.id.help_list);

        listHelp.setLayoutManager(new GridLayoutManager(getContext(), listColumns));

        return view;
    }

    @Override
    public void showProgressBar() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgressBar() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(String message) {
        Toast.makeText(requireContext(), getResources().getString(R.string.categories_json_error) + ": " + message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void setCategoryData(List<JSONCategory> categories) {
        List<HelpListItem> helpListItems = new ArrayList<>();
        for (JSONCategory category: categories) {
            int drawable_id = R.drawable.help_list_children;
            switch (category.id) {
                case Const.CATEGORY_CHILDREN:
                    drawable_id = R.drawable.help_list_children;
                    break;
                case Const.CATEGORY_ADULTS:
                    drawable_id = R.drawable.help_list_adults;
                    break;
                case Const.CATEGORY_SENIORS:
                    drawable_id = R.drawable.help_list_seniors;
                    break;
                case Const.CATEGORY_ANIMALS:
                    drawable_id = R.drawable.help_list_animals;
                    break;
                case Const.CATEGORY_EVENTS:
                    drawable_id = R.drawable.help_list_events;
                    break;
            }
            helpListItems.add(new HelpListItem(drawable_id, category.name));
        }
        listHelp.setAdapter(new HelpListAdapter(getContext(), helpListItems));
    }

}