package model.entity.satellite;

import java.time.LocalDate;
import java.util.Vector;

import model.entity.agency.Agency;
import model.entity.instrument.Instrument;

public class Satellite {

	private final int ID;
	private final LocalDate FirstMissionDate;
	private LocalDate LastMissionDate;
	private String name;
	private Vector<Instrument> instruments = new Vector<>();
	private Vector<Agency> agencies  = new Vector<>();
	
	public Satellite(int arg0, LocalDate arg1)
	{
		this.ID = arg0;
		this.FirstMissionDate = arg1;
	}
	
	public LocalDate getLastMissionDate() {
		return LastMissionDate;
	}

	public void setLastMissionDate(LocalDate lastMissionDate) {
		LastMissionDate = lastMissionDate;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getID() {
		return ID;
	}

	public LocalDate getFirstMissionDate() {
		return FirstMissionDate;
	}
	
	public Vector<Instrument> getInstruments() {
		return instruments;
	}

	public void setInstruments(Vector<Instrument> instruments) {
		this.instruments = instruments;
	}

	public Vector<Agency> getAgencies() {
		return agencies;
	}

	public void setAgencies(Vector<Agency> agencies) {
		this.agencies = agencies;
	}
	
	
}