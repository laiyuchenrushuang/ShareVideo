package videotool.hc.com.videotool;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ThumbnailUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by ly on 2019/5/27.
 */

public class HorizontalListViewAdapter extends BaseAdapter{
    private int[] mIconIDs;
    private Context mContext;
    private LayoutInflater mInflater;
    Bitmap iconBitmap;
    private int selectIndex = -1;

    public HorizontalListViewAdapter(Context context, int[] ids){
        this.mContext = context;
        this.mIconIDs = ids;
        mInflater=(LayoutInflater)mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);//LayoutInflater.from(mContext);
    }
    @Override
    public int getCount() {
        return mIconIDs.length;
    }
    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if(convertView==null){
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.horizontal_list_item, null);
            holder.mImage=(ImageView)convertView.findViewById(R.id.img_list_item);
            holder.video_text=(TextView) convertView.findViewById(R.id.video_text);
            convertView.setTag(holder);
        }else{
            holder=(ViewHolder)convertView.getTag();
        }
        if(position == selectIndex){
            convertView.setSelected(true);
        }else{
            convertView.setSelected(false);
        }
        iconBitmap = getPropThumnail(position);
        holder.mImage.setImageBitmap(iconBitmap);
        holder.video_text.setText("视频名称");
        return convertView;
    }

    private static class ViewHolder {
        private ImageView mImage;
        public TextView video_text;
    }
    private Bitmap getPropThumnail(int id){
//        Drawable d = mContext.getResources().getDrawable(id);
        Bitmap b = BitmapFactory.decodeResource(mContext.getResources(),mIconIDs[id]);
//        Bitmap b = bw.getBitmap();
//		Bitmap bb = BitmapUtil.getRoundedCornerBitmap(b, 100);
//        int w = mContext.getResources().getDimensionPixelOffset(R.dimen.thumnail_default_width);
//        int h = mContext.getResources().getDimensionPixelSize(R.dimen.thumnail_default_height);
//
//        Bitmap thumBitmap = ThumbnailUtils.extractThumbnail(b, w, h);

        return b;
    }
    public void setSelectIndex(int i){
        selectIndex = i;
    }

}
