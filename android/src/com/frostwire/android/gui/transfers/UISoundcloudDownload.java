/*
 * Created by Angel Leon (@gubatron), Alden Torres (aldenml)
 * Copyright (c) 2011-2016, FrostWire(R). All rights reserved.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.frostwire.android.gui.transfers;

import android.net.Uri;
import com.frostwire.android.gui.Librarian;
import com.frostwire.android.gui.services.Engine;
import com.frostwire.platform.Platforms;
import com.frostwire.search.soundcloud.SoundcloudSearchResult;
import com.frostwire.transfers.SoundcloudDownload;

import java.io.File;

/**
 * @author aldenml
 * @author gubatron
 */
public class UISoundcloudDownload extends SoundcloudDownload {

    private final TransferManager manager;

    public UISoundcloudDownload(TransferManager manager, SoundcloudSearchResult sr) {
        super(sr, Platforms.data(), Platforms.temp());
        this.manager = manager;
    }

    @Override
    public File previewFile() {
        return isComplete() ? getSavePath() : null;
    }

    @Override
    public void remove(boolean deleteData) {
        super.remove(deleteData);

        manager.remove(this);
    }

    @Override
    protected void onAfterMove() {
        String hash = String.valueOf(getDisplayName().hashCode());
        Engine.instance().notifyDownloadFinished(getDisplayName(), getSavePath(), hash);
        Librarian.instance().scan(Uri.fromFile(getSavePath().getAbsoluteFile()));
    }
}