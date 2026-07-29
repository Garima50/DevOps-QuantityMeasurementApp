CREATE TABLE IF NOT EXISTS quantity_measurements (

    id INT AUTO_INCREMENT PRIMARY KEY,

    first_value DOUBLE,

    first_unit VARCHAR(50),

    first_measurement_type VARCHAR(30),

    second_value DOUBLE,

    second_unit VARCHAR(50),

    second_measurement_type VARCHAR(30),

    operation VARCHAR(30),

    result_value DOUBLE,

    result_unit VARCHAR(50),

    result_measurement_type VARCHAR(30),
    

    numeric_result BOOLEAN,

    error_message VARCHAR(255)
);