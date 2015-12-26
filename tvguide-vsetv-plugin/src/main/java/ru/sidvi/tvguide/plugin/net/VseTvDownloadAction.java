package ru.sidvi.tvguide.plugin.net;

import ru.sidvi.tvguide.plugin.Event;
import ru.sidvi.tvguide.plugin.parser.Parser;

import java.io.InputStream;
import java.util.List;

/**
 * Created by sidvi on 26.12.2015.
 */
public class VseTvDownloadAction implements DownloadAction {
    private Parser parser;

    public VseTvDownloadAction(Parser parser) {
        this.parser = parser;
    }

    @Override
    public List<Event> download(InputStream downloaded) {
        return parser.parse(downloaded);
    }
}
