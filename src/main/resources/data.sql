DROP TABLE IF EXISTS elastic_config;

CREATE TABLE elastic_config (
    id int AUTO_INCREMENT PRIMARY KEY,
    index_name varchar(50) default '-',
    type varchar(50) default '-',
    field_name varchar(50) default 'description',
    sort_field varchar(50) default 'timestamp',
    query_size int default 500,
    is_descending int default 1,
);

INSERT INTO elastic_config (index_name, type) VALUES ('twitter*', 'elasticsearchlog');
