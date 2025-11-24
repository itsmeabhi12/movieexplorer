### Docker: Database Container

Docker is used for postgres sql. Start it in detached mode:

```bash
docker compose up -d
```

### Bonus Challenge B – Audit Log for Watchlist

I added an audit_logs table and an AuditLogService that records every CREATE, UPDATE, and DELETE action on watchlist items. Each log entry stores the requestId, actorId, target type/id, previous values, new values, and a diff of what changed. This provides clear traceability of all changes with minimal complexity.

Trade-offs:
– Extra DB writes
– Slightly more code/overhead.

Much easier debugging, full change history, and scalable design for future multi-user support.
