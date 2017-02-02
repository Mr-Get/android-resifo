package at.fh.swengb.android_resifo

import android.content.{ContentValues, Context}
import android.database.sqlite.{SQLiteDatabase, SQLiteOpenHelper}

/**
  * Created by Sabine on 16.01.2017.
  */
case class Db(context: Context) extends SQLiteOpenHelper(context, "mydb", null, 1) {

  override def onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int): Unit = ()

  override def onCreate(db: SQLiteDatabase): Unit = {
    //Tabelle Person
    val personDao = SqlitePersDao(db)
    personDao.init()

    //Tabelle Anmeldung
    val anmDao = SqliteAnmDao(db)
    anmDao.init()

    //Tabelle Hauptsitz
    val hwsDao = SqliteHwsDao(db)
    hwsDao.init()

    //Tabelle Abmeldung
    val abmDao = SqliteAbmDao(db)
    abmDao.init()
  }

  def mkPersDao(): SqlitePersDao = SqlitePersDao(getWritableDatabase)
  def mkAnmDao(): SqliteAnmDao = SqliteAnmDao(getWritableDatabase)
  def mkHwsDao(): SqliteHwsDao = SqliteHwsDao(getWritableDatabase)
  def mkAbmDao(): SqliteAbmDao = SqliteAbmDao(getWritableDatabase)
}

case class SqlitePersDao(db: SQLiteDatabase) extends BaseDao[PersoenlicheDaten]{
  def init(): Unit = db.execSQL("create table person " +
                                "(person_id INTEGER PRIMARY KEY ASC, " +
                                "nachname TEXT, " +
                                "vorname TEXT, " +
                                "nachnameAlt TEXT, " +
                                "geburtsdatum TEXT, " +
                                "geburtsort TEXT, " +
                                "geschlecht CHAR, " +
                                "religion TEXT, " +
                                "familienstand TEXT, " +
                                "staatsangehoerigkeit TEXT);");

  def insert(p: PersoenlicheDaten): Long = {
    val cv: ContentValues = mkContentValues(p)
    db.insert("person", null, cv)
  }

  def mkContentValues(p: PersoenlicheDaten): ContentValues = {
    val cv = new ContentValues
    cv.put("nachname", p.nachname)
    cv.put("vorname", p.vorname)
    cv.put("nachnameVorher", p.nachnameVorher)
    cv.put("gebDatum", p.gebDatum)
    cv.put("gebOrt", p.gebOrt)
    cv.put("geschlecht", p.geschlecht)
    cv.put("religion", p.religion)
    cv.put("famStand", p.famStand)
    cv.put("staat", p.staat)
    cv
  }

  def update(p:PersoenlicheDaten): Int = {
    db.update("person", mkContentValues(p),
        "nachname = ?, " +
        "vorname = ?, " +
        "nachnameVorher = ?, " +
        "gebDatum = ?, " +
        "gebOrt = ?," +
        "geschlecht = ?," +
        "religion = ?, " +
        "famStand = ? and" +
        "staat = ?", Array( p.nachname,
                            p.vorname,
                            p.nachnameVorher,
                            p.gebDatum,
                            p.gebOrt,
                            p.geschlecht,
                            p.religion,
                            p.famStand,
                            p.staat))
  }
}

case class SqliteAnmDao(db: SQLiteDatabase) extends BaseDao[AnmeldeDaten]{
  def init(): Unit = db.execSQL("create table anmeldung " +
                                "(anmeldung_id INTEGER PRIMARY KEY ASC, " +
                                "person_id INTEGER, " +
                                "strasse TEXT, " +
                                "hausnr INTEGER, " +
                                "stiege INTERGER, " +
                                "tuer INTERGER, " +
                                "plz INTEGER, " +
                                "ort TEXT, " +
                                "bundesland TEXT, " +
                                "zuzugAusAusland TEXT, " +
                                "hauptwohnsitz TEXT, " +
                                "unterkunftgeber TEXT); ");

  def insert(p: AnmeldeDaten): Long = {
    val cv: ContentValues = mkContentValues(p)
    db.insert("anmeldung", null, cv)
  }

  def mkContentValues(p: AnmeldeDaten): ContentValues = {
    val cv = new ContentValues
    cv.put("strasse", p.strasse)
    cv.put("hausnummer", p.hausnummer)
    cv.put("stiege", p.stiege)
    cv.put("tuer", p.tuer)
    cv.put("plz", p.plz)
    cv.put("ort", p.ort)
    cv.put("bundesland", p.bundesland)
    cv.put("zuzugAusAusland", p.zuzugAusAusland)
    cv.put("hauptwohnsitz", p.hauptwohnsitz)
    cv.put("unterkunftgeber", p.unterkunftgeber)
    cv
  }

  def update(p: AnmeldeDaten): Int = {
    db.update("anmeldung", mkContentValues(p),
        "strasse = ?, " +
        "hausnummer = ?, " +
        "stiege = ?, " +
        "tuer = ?, " +
        "plz = ?," +
        "ort = ?," +
        "bundesland = ?, " +
        "zuzugAusAusland = ?, " +
        "hauptwohnsitz = ? and" +
        "unterkunftgeber = ?", Array( p.strasse,
                                      p.hausnummer,
                                      p.stiege,
                                      p.tuer,
                                      p.plz,
                                      p.ort,
                                      p.bundesland,
                                      p.zuzugAusAusland,
                                      p.hauptwohnsitz,
                                      p.unterkunftgeber))
  }
}

case class SqliteHwsDao(db: SQLiteDatabase) extends BaseDao[HauptwohnsitzDaten]{
  def init(): Unit = db.execSQL("create table hauptsitz " +
                                " (hauptsitz_id INTEGER PRIMARY KEY ASC, " +
                                "person_id INTEGER, " +
                                "strasse TEXT, " +
                                "hausnr INTEGER, " +
                                "stiege INTERGER, " +
                                "tuer INTERGER, " +
                                "plz INTEGER, " +
                                "ort TEXT, " +
                                "bundesland TEXT); ");

  def insert(p: HauptwohnsitzDaten): Long = {
    val cv: ContentValues = mkContentValues(p)
    db.insert("hauptsitz", null, cv)
  }

  def mkContentValues(p: HauptwohnsitzDaten): ContentValues = {
    val cv = new ContentValues
    cv.put("strasse", p.strasse)
    cv.put("hausnummer", p.hausnummer)
    cv.put("stiege", p.stiege)
    cv.put("tuer", p.tuer)
    cv.put("plz", p.plz)
    cv.put("ort", p.ort)
    cv.put("bundesland", p.bundesland)
    cv
  }

  def update(p: HauptwohnsitzDaten): Int = {
    db.update("hauptsitz", mkContentValues(p),
      "strasse = ?, " +
        "hausnummer = ?, " +
        "stiege = ?, " +
        "tuer = ?, " +
        "plz = ?," +
        "ort = ? and" +
        "bundesland = ? ", Array( p.strasse,
                                  p.hausnummer,
                                  p.stiege,
                                  p.tuer,
                                  p.plz,
                                  p.ort,
                                  p.bundesland))
  }
}

case class SqliteAbmDao(db: SQLiteDatabase) extends BaseDao[AbmeldeDaten]{
  def init(): Unit = db.execSQL("create table abmeldung" +
                                " (abmeldung_id INTEGER PRIMARY KEY ASC, " +
                                "person_id INTEGER, " +
                                "strasse TEXT, " +
                                "hausnr INTEGER, " +
                                "stiege INTERGER, " +
                                "tuer INTERGER, " +
                                "plz INTEGER, " +
                                "ort TEXT, " +
                                "bundesland TEXT, " +
                                "verzugInsAusland TEXT);")

  def insert(p: AbmeldeDaten): Long = {
    val cv: ContentValues = mkContentValues(p)
    db.insert("abmeldung", null, cv)
  }

  def mkContentValues(p: AbmeldeDaten): ContentValues = {
    val cv = new ContentValues
    cv.put("strasse", p.strasse)
    cv.put("hausnummer", p.hausnummer)
    cv.put("stiege", p.stiege)
    cv.put("tuer", p.tuer)
    cv.put("plz", p.plz)
    cv.put("ort", p.ort)
    cv.put("bundesland", p.bundesland)
    cv.put("verzugInsAusland", p.zuzugAusAusland)
    cv
  }

  def update(p: AbmeldeDaten): Int = {
    db.update("abmeldung", mkContentValues(p),
      "strasse = ?, " +
        "hausnummer = ?, " +
        "stiege = ?, " +
        "tuer = ?, " +
        "plz = ?," +
        "ort = ?," +
        "bundesland = ? and " +
        "verzugInsAusland = ?", Array(  p.strasse,
                                        p.hausnummer,
                                        p.stiege,
                                        p.tuer,
                                        p.plz,
                                        p.ort,
                                        p.bundesland,
                                        p.zuzugAusAusland))
  }
}

trait BaseDao[T]{
  def insert(t: T): Long
  def update(t: T): Int
}