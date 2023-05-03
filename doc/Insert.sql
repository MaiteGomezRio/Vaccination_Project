INSERT INTO Doctor (id, name, surname)
VALUES (1,'Angeles', 'Paquiderma');
INSERT INTO Doctor (id, name, surname)
VALUES (2,'Luis','De la Serna');

INSERT INTO Disease(id, name)
VALUES (1, 'Haemophilus');
INSERT INTO Disease(id, name)
VALUES(2,'Measles');
INSERT INTO Disease(id, name)
VALUES(3,'Pneumococcal');


INSERT INTO Vaccine (id, name, dose)
VALUES (1, 'Pfizer',3);
INSERT INTO Vaccine (id, name, dose)
VALUES(2,'VPI',4);

INSERT INTO Condition(id, type)
VALUES (1, 'Pregnant');
INSERT INTO Condition(id, type)
VALUES (2, 'Allergies');

INSERT INTO Patient(id, name, surname, immune, attendance)
VALUES (6, 'Maria', 'Gonzalez',true,true);
INSERT INTO Patient(id, name, surname, immune, attendance)
VALUES (2, 'Clara', 'Prieto', false, false);

INSERT INTO Condition_Vaccine(vaccine_id, condition_id)
VALUES(1,1);
INSERT INTO Condition_Vaccine(vaccine_id, condition_id)
VALUES(1,2);
