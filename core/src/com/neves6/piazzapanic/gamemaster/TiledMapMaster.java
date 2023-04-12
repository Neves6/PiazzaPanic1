package com.neves6.piazzapanic.gamemaster;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Rectangle;

public class TiledMapMaster {
  TiledMap map;
  int tileWidth;

  public TiledMapMaster(TiledMap map) {
    this.map = map;
    // It is a square hence, width = height, just get one.
    tileWidth = (int) map.getProperties().get("tilewidth");
  }
  /**
   * Helper method used to get the objects from a layer using its key.
   *
   * @param key Name of the layer.
   * @return All objects from the layer indicated by the key.
   */
  public MapObjects getObjectLayers(String key) {
    MapLayer unlockLayer = map.getLayers().get(key);
    return unlockLayer.getObjects();
  }

  /**
   * Converts the tiled map coordinates into the game coordinates then compares it to the target x
   * and target y, to check whether the rectangle is being interacted with.
   *
   * @param object A tiled map representation of an point on the map.
   * @param xcoord A game x coordinate.
   * @param ycoord A game y coordinate.
   * @return Boolean value representing whether the coordinates passed in are the coordinates of the
   *     rectangle passed in.
   */
  public Boolean detectInteractionFromTiledObject(Rectangle object, int xcoord, int ycoord) {
    return xcoord == Math.round(object.getX() / tileWidth)
        && ycoord == Math.round(object.getY() / tileWidth);
  }

  /**
   * Helper method to convert a map object to a rectangle map object.
   *
   * @param object A single part of a layer of the map.
   * @return Returns a rectangle representing the object.
   */
  public Rectangle loadRectangle(MapObject object) {
    RectangleMapObject rectangleMapObject = (RectangleMapObject) object;
    // Now you can get the position of the rectangle like this:
    return rectangleMapObject.getRectangle();
  }
}
