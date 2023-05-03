CREATE TABLE Doctor(
	id INTEGER PRIMARY KEY AUTOINCREMENT,
	name TEXT NOT NULL,
	surname TEXT NOT NULL
);

CREATE TABLE Patient(
	id TEXT PRIMARY KEY AUTOINCREMENT,
	name TEXT NOT NULL,
	surname TEXT NOT NULL,
	attendance BOOLEAN,
	immune BOOLEAN
);
CREATE TABLE Vaccine (
	id INTEGER PRIMARY KEY AUTOINCREMENT,
	name TEXT NOT NULL,
	dose INTEGER 
);

CREATE TABLE Condition(
	id INTEGER PRIMARY KEY AUTOINCREMENT,
	type TEXT NOT NULL
);

CREATE TABLE Disease(
	id INTEGER PRIMARY KEY AUTOINCREMENT,
	name TEXT NOT NULL
);

CREATE TABLE Condition_Vaccine(
	vaccine_id INTEGER,
	condition_id INTEGER,
	FOREIGN KEY (vaccine_id) REFERENCES Vaccine(id),
	FOREIGN KEY (condition_id) REFERENCES Condition (id)
);
CREATE TABLE Patient_Disease(
	patient_id TEXT NOT NULL,
	disease_id INTEGER,
	FOREIGN KEY (patient_id) REFERENCES Patient(id),
	FOREIGN KEY (disease_id) REFERENCES Disease(id),
);
CREATE TABLE Disease_Vaccine(
	disease_id INTEGER,
	vaccine_id INTEGER,
	FOREIGN KEY (disease_id) REFERENCES Disease(id),
	FOREIGN KEY (vaccine_id) REFERENCES Vaccine(id),
);
CREATE TABLE Immune(
	patient_id TEXT NOT NULL,
	disease_id INTEGER,
	FOREIGN KEY (patient_id) REFERENCES Patient(id),
	FOREIGN KEY (disease_id) REFERENCES Disease(id),
);
