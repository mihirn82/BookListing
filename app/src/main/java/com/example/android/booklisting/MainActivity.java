package com.example.android.booklisting;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    /**
     * URL for book data from the Google API dataset
     */
    private static final String GOOGLE_REQUEST_URL = "https://www.googleapis.com/books/v1/volumes?q=";

    public static final String LOG_TAG = MainActivity.class.getName();

    /**
     * Adapter for the list of books
     */
    private BookAdapter mAdapter;

    /**
     * TextView that is displayed when the list is empty
     */
    private TextView mEmptyStateTextView;

    private ProgressBar mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mProgress = (ProgressBar) findViewById(R.id.ProgressBar);
        mProgress.setVisibility(View.GONE);

        // Find a reference to the {@link ListView} in the layout
        ListView bookListView = (ListView) findViewById(R.id.list);
        mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
        bookListView.setEmptyView(mEmptyStateTextView);
        mEmptyStateTextView.setText(R.string.enter_keyword);

        // Find the View that shows the search button
        Button search = (Button) findViewById(R.id.button);
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditText text = (EditText) findViewById(R.id.keyword);
                String keyword = text.getText().toString();

                StringBuilder strBuilder = new StringBuilder(GOOGLE_REQUEST_URL);
                strBuilder.append(keyword);
                String url = strBuilder.toString();

                if (keyword.toString().trim().length() > 0) {
                    // Create an {@link AsyncTask} to perform the HTTP request to the given URL
                    // on a background thread. When the result is received on the main UI thread,
                    // then update the UI.
                    BookAsyncTask task = new BookAsyncTask();
                    task.execute(url);

                    // Find a reference to the {@link ListView} in the layout
                    ListView bookListView = (ListView) findViewById(R.id.list);

                    mProgress = (ProgressBar) findViewById(R.id.ProgressBar);
                    mProgress.setVisibility(View.VISIBLE);

                    mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
                    bookListView.setEmptyView(mEmptyStateTextView);
                    mEmptyStateTextView.setText(R.string.enter_keyword);

                    // Create a new {@link ArrayAdapter} of books
                    mAdapter = new BookAdapter(MainActivity.this, new ArrayList<Book>());

                    // Set the adapter on the {@link ListView}
                    // so the list can be populated in the user interface
                    bookListView.setAdapter(mAdapter);

                    ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                    if (networkInfo != null && networkInfo.isConnected()) {
                        Log.v("MainActivity.java", "Done!!!");
                    } else {
                        mProgress.setVisibility(View.GONE);
                        mEmptyStateTextView.setText(R.string.no_internet);
                    }
                }
                if (keyword.toString().trim().length() == 0) {

                    mProgress = (ProgressBar) findViewById(R.id.ProgressBar);
                    mProgress.setVisibility(View.GONE);

                    // Find a reference to the {@link ListView} in the layout
                    ListView bookListView = (ListView) findViewById(R.id.list);
                    mEmptyStateTextView = (TextView) findViewById(R.id.empty_view);
                    bookListView.setEmptyView(mEmptyStateTextView);

                    // Create a new {@link ArrayAdapter} of books
                    mAdapter = new BookAdapter(MainActivity.this, new ArrayList<Book>());
                    // Set the adapter on the {@link ListView}
                    // so the list can be populated in the user interface
                    bookListView.setAdapter(mAdapter);

                    mEmptyStateTextView.setText(R.string.no_books);

                    ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
                    NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
                    if (networkInfo != null && networkInfo.isConnected()) {
                        Log.v("MainActivity.java", "Done!!!");
                    } else {
                        mProgress.setVisibility(View.GONE);
                        mEmptyStateTextView.setText(R.string.no_internet);
                    }
                }
            }
        });
    }

    private class BookAsyncTask extends AsyncTask<String, Void, List<Book>> {

        @Override
        protected List<Book> doInBackground(String... urls) {

            // Don't perform the request if there are no URLs, or the first URL is null.
            if (urls.length < 1 || urls[0] == null) {
                return null;
            }

            List<Book> result = QueryUtils.fetchBookData(urls[0]);
            return result;
        }

        @Override
        protected void onPostExecute(List<Book> data) {

            // Clear the adapter of previous book data
            mAdapter.clear();

            // If there is a valid list of {@link Book}s, then add them to the adapter's
            // data set. This will trigger the ListView to update.
            if (data != null && !data.isEmpty()) {
                mAdapter.addAll(data);
            }
        }
    }
}
