package com.example.android.booklisting;

import android.app.Activity;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;


/**
 * Created by mihirnewalkar on 12/21/16.
 */

public class BookAdapter extends ArrayAdapter<Book> {

    /**
     * This is our own custom constructor (it doesn't mirror a superclass constructor).
     * The context is used to inflate the layout file, and the list is the data we want
     * to populate into the lists.
     *
     * @param context        The current context. Used to inflate the layout file.
     * @param books A List of book objects to display in a list
     */
    public BookAdapter(Activity context, ArrayList<Book> books) {
        // Here, we initialize the ArrayAdapter's internal storage for the context and the list.
        // the second argument is used when the ArrayAdapter is populating a single TextView.
        // Because this is a custom adapter for two TextViews and an ImageView, the adapter is not
        // going to use this second argument, so it can be any value. Here, we used 0.
        super(context, 0, books);
    }

    /**
     * Provides a view for an AdapterView (ListView, GridView, etc.)
     *
     * @param position The position in the list of data that should be displayed in the
     *                 list item view.
     * @param convertView The recycled view to populate.
     * @param parent The parent ViewGroup that is used for inflation.
     * @return The View for the position in the AdapterView.
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Check if the existing view is being reused, otherwise inflate the view
        View listItemView = convertView;
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        // Get the {@link AndroidFlavor} object located at this position in the list
        Book currentBook = getItem(position);

        // Find the ImageView in the list_item.xml layout with thumbnail
        ImageView thumbnailView = (ImageView) listItemView.findViewById(R.id.image);
        Bitmap sThumbnail = currentBook.getThumbnail();
        thumbnailView.setImageBitmap(sThumbnail);

        // Find the TextView in the list_item.xml layout with title
        TextView titleView = (TextView) listItemView.findViewById(R.id.title_text_view);
        // Get the name from the current Book object and
        // set this text on the title TextView
        String sTitle = currentBook.getTitle();
        titleView.setText(sTitle);

        // Find the TextView in the list_item.xml layout with authors
        TextView authorsView = (TextView) listItemView.findViewById(R.id.authors_text_view);
        String sAuthors = currentBook.getAuthors();
        authorsView.setText(sAuthors);

        return listItemView;
    }
}
