package com.example.android.booklisting;

import android.graphics.Bitmap;

/**
 * Created by mihirnewalkar on 12/21/16.
 */

public class Book {

    //Image of the book.
    private Bitmap mThumbnail;

    //Title of the book.
    private String mTitle;

    //Author of the book.
    private String mAuthors;

    /*
    * Create a new Book object.
    *
    *@param title
    * */
    public Book(Bitmap thumbnail, String title, String authors)
    {
        mThumbnail = thumbnail;
        mTitle = title;
        mAuthors = authors;
    }

    public Bitmap getThumbnail() {return mThumbnail; }

    public String getTitle(){
        return mTitle;
    }

    public String getAuthors(){
        return mAuthors;
    }
}
