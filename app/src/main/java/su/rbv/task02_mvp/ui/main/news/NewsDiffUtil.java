package su.rbv.task02_mvp.ui.main.news;

import androidx.recyclerview.widget.DiffUtil;
import java.util.List;
import su.rbv.task02_mvp.data.JSONEvent;

public class NewsDiffUtil extends DiffUtil.Callback {

    private final List<JSONEvent> oldList;
    private final List<JSONEvent> newList;

    public NewsDiffUtil(List<JSONEvent> oldList, List<JSONEvent> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList.size();
    }

    @Override
    public int getNewListSize() {
        return newList.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return oldList.get(oldItemPosition).id.equals(newList.get(newItemPosition).id);

    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        JSONEvent oldEvent = oldList.get(oldItemPosition);
        JSONEvent newEvent = newList.get(newItemPosition);

        if (oldEvent.startDate != newEvent.startDate || oldEvent.endDate != newEvent.endDate) {
            return false;
        }

        if ((oldEvent.name == null && newEvent.name != null) ||
            (oldEvent.name != null && newEvent.name == null) ||
            (oldEvent.description == null && newEvent.description != null) ||
            (oldEvent.description != null && newEvent.description == null)
        ) {
            return false;
        }

        return (oldEvent.name == null || oldEvent.name.equals(newEvent.name)) &&
               (oldEvent.description == null || oldEvent.description.equals(newEvent.description));
    }

}
