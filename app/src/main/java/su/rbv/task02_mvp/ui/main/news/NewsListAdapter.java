package su.rbv.task02_mvp.ui.main.news;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.threeten.bp.Instant;
import org.threeten.bp.LocalDate;
import org.threeten.bp.LocalDateTime;
import org.threeten.bp.ZoneId;
import org.threeten.bp.temporal.ChronoUnit;

import java.util.List;

import su.rbv.task02_mvp.Const;
import su.rbv.task02_mvp.R;
import su.rbv.task02_mvp.data.JSONEvent;

public class NewsListAdapter extends RecyclerView.Adapter<NewsListAdapter.NewsListViewHolder> {

    private static final int TEN = 10;

    public static class NewsListViewHolder extends RecyclerView.ViewHolder {
        public TextView name;
        public TextView body;
        public TextView date;
        public View layout;

        NewsListViewHolder(View v) {
            super(v);
            name = v.findViewById(R.id.list_news_item_name);
            body = v.findViewById(R.id.list_news_item_body);
            date = v.findViewById(R.id.list_news_item_date);
            layout = v.findViewById(R.id.list_news_item_layout);
        }
    }

    private List<JSONEvent> events;
    private Context ctx;
    private NewsPresenter newsPresenter;

    NewsListAdapter(Context ctx, List<JSONEvent> events, NewsPresenter newsPresenter) {
        super();
        this.ctx = ctx;
        this.events = events;
        this.newsPresenter = newsPresenter;
    }

    public void setData(List<JSONEvent> events) {
        this.events = events;
    }

    public List<JSONEvent> getData() {
        return events;
    }

    @NonNull
    @Override
    public NewsListViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new NewsListViewHolder(LayoutInflater.from(ctx).inflate(R.layout.list_news_item, viewGroup, false));
    }

    @Override
    public int getItemCount() {
        return events.size();
    }

    @Override
    public void onBindViewHolder(@NonNull NewsListViewHolder viewHolder, final int position) {

        JSONEvent item = events.get(position);
        viewHolder.name.setText(item.name);
        viewHolder.body.setText(item.description);

        String strDate;
        try {
            LocalDate currentDate = LocalDate.now();
            LocalDate date1 = LocalDateTime.ofInstant(Instant.ofEpochMilli(item.startDate), ZoneId.systemDefault()).toLocalDate();
            LocalDate date2 = LocalDateTime.ofInstant(Instant.ofEpochMilli(item.endDate), ZoneId.systemDefault()).toLocalDate();

            if (date1.isEqual(date2)) {
                strDate = Const.months[date1.getMonthValue() - 1] + " " + date1.getDayOfMonth() + ", " + date1.getYear();
            } else {
                strDate = date1.getDayOfMonth() + "." + String.format("%02d", date1.getMonthValue()) +
                        " - " + date2.getDayOfMonth() + "." + String.format("%02d", date2.getMonthValue());
            }

            if (date1.isAfter(currentDate)) {
                long deltaDays = currentDate.until(date1, ChronoUnit.DAYS);
                int lastDeltaDay = (int) deltaDays % TEN;
                strDate =
                        ctx.getResources().getQuantityString(R.plurals.left, lastDeltaDay, (int) deltaDays) + " " +
                        ctx.getResources().getQuantityString(R.plurals.days, lastDeltaDay, (int) deltaDays) + " (" + strDate + ")";
            }
        } catch (Exception e) {
            strDate = Const.INCORRECT_DATE_FORMAT;
        }

        final String strDateDetail = strDate;
        viewHolder.date.setText(strDate);
        viewHolder.layout.setOnClickListener((v) -> newsPresenter.openEventDetailFragment(item, strDateDetail));
    }

}
