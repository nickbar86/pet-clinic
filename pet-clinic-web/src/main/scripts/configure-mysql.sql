CREATE DATABASE pet_clinic_dev;

CREATE USER 'pet_clinic_dev_user'@'localhost' IDENTIFIED BY 'app';
CREATE USER 'pet_clinic_dev_user'@'%' IDENTIFIED BY 'app';

GRANT SELECT ON pet_clinic_dev.* to 'pet_clinic_dev_user'@'localhost';
GRANT INSERT ON pet_clinic_dev.* to 'pet_clinic_dev_user'@'localhost';
GRANT DELETE ON pet_clinic_dev.* to 'pet_clinic_dev_user'@'localhost';
GRANT UPDATE ON pet_clinic_dev.* to 'pet_clinic_dev_user'@'localhost';

GRANT SELECT ON pet_clinic_dev.* to 'pet_clinic_dev_user'@'%';
GRANT INSERT ON pet_clinic_dev.* to 'pet_clinic_dev_user'@'%';
GRANT DELETE ON pet_clinic_dev.* to 'pet_clinic_dev_user'@'%';
GRANT UPDATE ON pet_clinic_dev.* to 'pet_clinic_dev_user'@'%';