package com.makeveryday.ahnjunhyeock.criminalintent.database;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.makeveryday.ahnjunhyeock.criminalintent.Crime;

import java.util.Date;
import java.util.UUID;

import static com.makeveryday.ahnjunhyeock.criminalintent.database.CrimeDbSchema.*;

/**
 * Created by ahnjunhyeock on 2017. 12. 4..
 */

public class CrimeCursorWrapper extends CursorWrapper {

    public CrimeCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Crime getCrime() {
        String uuidString = getString(getColumnIndex(CrimeTable.Cols.UUID));
        String title = getString(getColumnIndex(CrimeTable.Cols.TITLE));
        long date = getLong(getColumnIndex(CrimeTable.Cols.DATE));
        int isSolved = getInt(getColumnIndex(CrimeTable.Cols.SOLVED));

        Crime crime = new Crime(UUID.fromString(uuidString));
        crime.setTitle(title);
        crime.setDate(new Date(date));
        crime.setSolved(isSolved != 0);
        return null;
    }
}
