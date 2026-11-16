# SqliteLibrary

Legacy Android library (Eclipse project) that wraps SQLite with annotations and a chained query API.

This is an older sample kept for reference. It is not actively maintained.

## What it contains

- Annotations `@TableName` and `@Column` to map a class to a table
- `Database`: `getSingleInstance(context)`, `init()`, `select()`, `select(columns)`, `where(WMap)`,
  `orderBY(columns, order)` and `execute(...)` with a `Callback`
- `DataBaseHelper`, `DBCodes` and the `WMap` helper for where clauses
- Bundled libraries: Android support v4 and Apache Commons Lang 3.4

## Notes

- Eclipse project layout (`SqliteLibrary/src`, `gen`, `bin`). Build output (`bin/`, `.class` files) is committed.
- For new projects, use Room or another maintained library.

Third-party libraries bundled as `.jar` files keep their own licences.

## License

MIT. See [LICENSE](LICENSE).
