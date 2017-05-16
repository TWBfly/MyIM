package club.ah12530.myim.adapter;


import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import club.ah12530.myim.R;
import club.ah12530.myim.uitls.StringUtils;

public class ContactAdapter extends RecyclerView.Adapter implements IContactAdapter {
    private List<String> contactList;

    public ContactAdapter(List<String> contactList) {
        this.contactList = contactList;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_contact, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        String contact = contactList.get(position);
        ((ViewHolder)holder).tvUsername.setText(contact);
        String initial = StringUtils.getInitial(contact);
        ((ViewHolder)holder).tvSection.setText(initial);

        if (position==0){
            ((ViewHolder)holder).tvSection.setVisibility(View.VISIBLE);
        }else {
            //判断与上一个首字母是否相同,相同gone,不相同显示
            String preContact = contactList.get(position - 1);
            String preInitial = StringUtils.getInitial(preContact);//得到上一个首字母
            if (preInitial.equals(initial)){
                ((ViewHolder)holder).tvSection.setVisibility(View.GONE);
            }else {
                ((ViewHolder)holder).tvSection.setVisibility(View.VISIBLE);
            }
        }

    }

    @Override
    public int getItemCount() {
        return contactList==null ? 0 : contactList.size();

    }

    @Override
    public List<String> getData() {
        return contactList;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.tv_section)
        TextView tvSection;
        @BindView(R.id.tv_username)
        TextView tvUsername;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
