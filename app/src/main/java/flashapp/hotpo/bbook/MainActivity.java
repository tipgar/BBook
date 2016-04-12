package flashapp.hotpo.bbook;

import android.content.res.AssetManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import nl.siegmann.epublib.domain.Book;
import nl.siegmann.epublib.domain.TOCReference;
import nl.siegmann.epublib.epub.EpubReader;

public class MainActivity extends AppCompatActivity {

    /**
     * Called when the activity is first created.
     */

    public TextView tv;
    public String text = "";

    @Override

    public void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        tv = (TextView) findViewById(R.id.tvContent);
        AssetManager assetManager = getAssets();

        try {

            // find InputStream for book

            InputStream epubInputStream = assetManager

                    .open("books/littlePrince.epub");


            // Load Book from inputStream

            Book book = (new EpubReader()).readEpub(epubInputStream);


            // Log the book's authors

            Log.i("epublib", "author(s): " + book.getMetadata().getAuthors());


            // Log the book's title

            Log.i("epublib", "title: " + book.getTitle());


            // Log the book's coverimage property

//            Bitmap coverImage = BitmapFactory.decodeStream(book.getCoverImage()
//                    .getInputStream());

//            Log.i("epublib", "Coverimage is " + coverImage.getWidth() + " by "
//                    + coverImage.getHeight() + " pixels");


            //book content


            // Log the table of contents

            logTableOfContents(book.getTableOfContents().getTocReferences(), 0);

            //text=book.getTableOfContents().getTocReferences().toString();
            text = book.getTitle() + "-----" + GetTableOfContent(book.getTableOfContents().getTocReferences(), 0)
                    + "--------" + book.getMetadata().getAuthors();
            if (tv != null) {
                tv.setText(text);

            }

        } catch (IOException e) {

            Log.e("epublib", e.getMessage());

        }

    }


    /**
     * Recursively Log the Table of Contents
     *
     * @param tocReferences
     * @param depth
     */
    private void logTableOfContents(List<TOCReference> tocReferences, int depth) {

        if (tocReferences == null) {

            return;

        }

        for (TOCReference tocReference : tocReferences) {

            StringBuilder tocString = new StringBuilder();

            for (int i = 0; i < depth; i++) {

                tocString.append("\t");

            }

            tocString.append(tocReference.getTitle());

            Log.i("epublib", tocString.toString());


            logTableOfContents(tocReference.getChildren(), depth + 1);

        }

    }

    private String GetTableOfContent(List<TOCReference> tocReferences, int depth) {

        StringBuilder tocString = new StringBuilder();
        if (tocReferences == null) {

            return tocString.toString();

        }

        for (TOCReference tocReference : tocReferences) {


            for (int i = 0; i < depth; i++) {

                tocString.append("\t");

            }

            tocString.append(tocReference.getTitle());

            Log.i("epublib", tocString.toString());


            logTableOfContents(tocReference.getChildren(), depth + 1);

        }
        return tocString.toString();

    }

}
