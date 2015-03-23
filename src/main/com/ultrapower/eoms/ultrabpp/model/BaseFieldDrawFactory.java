package com.ultrapower.eoms.ultrabpp.model;

public abstract class BaseFieldDrawFactory
{
	public static BaseFieldDraw<?> getDraw(BaseField baseField)
	{
		BaseFieldDraw baseDraw = null;
		if (baseField != null) {
			String name = baseField.getClass().getCanonicalName();
			name = name.substring(0, name.length() - 5) + "Draw" + BaseFieldDrawType.PC;
			try {
				Object newInstance = Class.forName(name).newInstance();
				if (newInstance != null) {
					baseDraw = (BaseFieldDraw) newInstance;
				}
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return baseDraw;
	}
}
