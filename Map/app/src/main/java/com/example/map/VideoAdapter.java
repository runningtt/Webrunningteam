package com.example.map;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.LayoutRes;

import com.bumptech.glide.Glide;
import com.shuyu.gsyvideoplayer.utils.OrientationUtils;
import com.shuyu.gsyvideoplayer.video.StandardGSYVideoPlayer;

import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


public class VideoAdapter extends BaseAdapter {
    private LayoutInflater _inflater;
    private List<Map<String, Object>> _data;
    private @LayoutRes int _layout;
    private Context _ctx;
    private StandardGSYVideoPlayer curPlayer;
    protected OrientationUtils orientationUtils;
    protected boolean isPlay;
    protected boolean isFull;
    String[] videos;
    String[] titles;
    String[] heads;
    public static final String TAG = "List";

    public  VideoAdapter(Context context, @LayoutRes int layoutId,
                         List<Map<String, Object>> data, String[] videos, String[] titles, String[] heads){
        this._ctx = context;
        this._inflater = LayoutInflater.from(_ctx);
        this._layout = layoutId;
        this._data = data;
        this.videos = videos;
        this.titles = titles;
        this.heads = heads;

    }

    @Override
    public int getCount(){
        return _data.size();
    }

    @Override
    public Object getItem(int position){
        return _data.get(position);
    }

    @Override
    public long getItemId(int position){
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent){
        final int i = position;
        final ViewHolder vh;
        if(convertView == null){
            convertView = _inflater.inflate(_layout, parent, false);
            vh = new ViewHolder();
            vh.bt1 = convertView.findViewById(R.id.head);
            vh.bt2 = convertView.findViewById(R.id.share);

            vh.tv1 = convertView.findViewById(R.id.name);
            vh.tv2 = convertView.findViewById(R.id.text);
            vh.gsyVideoPlayer = (StandardGSYVideoPlayer)convertView.findViewById(R.id.video_item_player);
            convertView.setTag(vh);
        }else{
            vh = (ViewHolder)convertView.getTag();
        }
        Glide.with(convertView.getContext()).load(heads[i]).into(vh.bt1);
        vh.bt2.setImageResource((int)_data.get(position).get("share"));
        vh.bt2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(_ctx, String.format(_ctx.getResources().getString(R.string.share_clicked), i),
                        Toast.LENGTH_SHORT).show();

            }
        });
        vh.tv1.setText(_data.get(i).get("name").toString());
        vh.tv2.setText(_data.get(i).get("message").toString());

        final String url = videos[i];
        final String title = titles[i];

        vh.gsyVideoPlayer.setUpLazy(url, true, null, null, title);
        vh.gsyVideoPlayer.getTitleTextView().setVisibility(View.GONE);
        vh.gsyVideoPlayer.getBackButton().setVisibility(View.GONE);
        vh.gsyVideoPlayer.getFullscreenButton().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vh.gsyVideoPlayer.startWindowFullscreen(_ctx, false, true);
            }
        });
        vh.gsyVideoPlayer.setPlayTag(TAG);
        vh.gsyVideoPlayer.setPlayPosition(i);
        vh.gsyVideoPlayer.setAutoFullWithSize(true);
        vh.gsyVideoPlayer.setReleaseWhenLossAudio(false);
        vh.gsyVideoPlayer.setShowFullAnimation(true);
        vh.gsyVideoPlayer.setIsTouchWiget(false);





        convertView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
            @Override
            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

            }
        });
        return convertView;
    }

    public static class ViewHolder{
        CircleImageView bt1;
        ImageButton bt2;
        TextView tv1;
        TextView tv2;
        StandardGSYVideoPlayer gsyVideoPlayer;
    }
}
