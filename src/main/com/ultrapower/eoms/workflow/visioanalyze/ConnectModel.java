package com.ultrapower.eoms.workflow.visioanalyze;

public class ConnectModel
{
	private String fromSheet;
	
	private String fromCell;
	
	private String toSheet;
	
	private String toCell;
	
	private String toPart;

	public String getFromSheet()
	{
		return fromSheet;
	}

	public void setFromSheet(String fromSheet)
	{
		this.fromSheet = fromSheet;
	}

	public String getFromCell()
	{
		return fromCell;
	}

	public void setFromCell(String fromCell)
	{
		this.fromCell = fromCell;
	}

	public String getToSheet()
	{
		return toSheet;
	}

	public void setToSheet(String toSheet)
	{
		this.toSheet = toSheet;
	}

	public String getToCell()
	{
		return toCell;
	}

	public void setToCell(String toCell)
	{
		this.toCell = toCell;
	}

	public String getToPart()
	{
		return toPart;
	}

	public void setToPart(String toPart)
	{
		this.toPart = toPart;
	}
}
