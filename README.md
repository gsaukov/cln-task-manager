Development road map for existing task manager (ETM):
1. Analyze, cleanup and fix critical issues.
1.1 FileService should not know anything about tasks. Task service should not know anything about files. 
1.2
2. Add liquibase schema and h2 support for ETM.
3. Cover with ETM with unit and component tests.
4. Introduce database managed concurrency (OPTIMISTIC locking).
5. Review and consolidate code under same style.
