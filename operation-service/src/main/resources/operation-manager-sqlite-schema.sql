CREATE TABLE task (  
    id   INTEGER PRIMARY KEY,  
    name TEXT    NOT NULL UNIQUE,  
    note TEXT  
);  

CREATE TABLE executor (  
    id   INTEGER PRIMARY KEY,  
    name TEXT    NOT NULL UNIQUE,  
    note TEXT  
);  

CREATE TABLE period (  
    id         INTEGER  PRIMARY KEY,  
    name       TEXT     NOT NULL UNIQUE,  
    note       TEXT,  
    start_time DATETIME NOT NULL,  
    end_time   DATETIME NOT NULL,  
    CHECK (start_time <= end_time),  
    UNIQUE (start_time, end_time)  
);  

CREATE TABLE op_group (  
    id   INTEGER PRIMARY KEY,  
    name TEXT    NOT NULL UNIQUE,  
    note TEXT  
);  

CREATE TABLE op (  
    id          INTEGER PRIMARY KEY,  
    name        TEXT    NOT NULL,  
    note        TEXT,  
    status      TEXT    NOT NULL,  
    profit      REAL    NOT NULL CHECK (profit > 0),  
    cost        REAL    NOT NULL CHECK (cost > 0),  
    op_group_id INTEGER NOT NULL REFERENCES op_group (id) ON DELETE RESTRICT,  
    task_id     INTEGER NOT NULL REFERENCES task (id) ON DELETE RESTRICT,  
    executor_id INTEGER NOT NULL REFERENCES executor (id) ON DELETE RESTRICT,  
    period_id   INTEGER NOT NULL REFERENCES period (id) ON DELETE RESTRICT,  
    UNIQUE (task_id, executor_id, period_id),  
    UNIQUE (name, op_group_id)  
);