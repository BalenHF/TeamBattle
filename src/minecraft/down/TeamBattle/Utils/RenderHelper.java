package down.TeamBattle.Utils;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.util.AxisAlignedBB;

import org.lwjgl.opengl.GL11;

public final class RenderHelper {
	public static void drawBorderedRect(final float x, final float y,
			final float x2, final float y2, final float l1, final int col1,
			final int col2) {
		drawRect(x, y, x2, y2, col2);

		final float f = (col1 >> 24 & 0xFF) / 255F;
		final float f1 = (col1 >> 16 & 0xFF) / 255F;
		final float f2 = (col1 >> 8 & 0xFF) / 255F;
		final float f3 = (col1 & 0xFF) / 255F;

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);

		GL11.glPushMatrix();
		GL11.glColor4f(f1, f2, f3, f);
		GL11.glLineWidth(l1);
		GL11.glBegin(GL11.GL_LINES);
		GL11.glVertex2d(x, y);
		GL11.glVertex2d(x, y2);
		GL11.glVertex2d(x2, y2);
		GL11.glVertex2d(x2, y);
		GL11.glVertex2d(x, y);
		GL11.glVertex2d(x2, y);
		GL11.glVertex2d(x, y2);
		GL11.glVertex2d(x2, y2);
		GL11.glEnd();
		GL11.glPopMatrix();

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
	}

	public static void drawFilledBox(AxisAlignedBB par1AxisAlignedBB) {
		final WorldRenderer tessellator = Tessellator.instance.getWorldRenderer();
		tessellator.startDrawingQuads();
		tessellator.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY,
				par1AxisAlignedBB.minZ);
		tessellator.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY,
				par1AxisAlignedBB.minZ);
		tessellator.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY,
				par1AxisAlignedBB.minZ);
		tessellator.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY,
				par1AxisAlignedBB.minZ);
		tessellator.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY,
				par1AxisAlignedBB.maxZ);
		tessellator.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY,
				par1AxisAlignedBB.maxZ);
		tessellator.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY,
				par1AxisAlignedBB.maxZ);
		tessellator.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY,
				par1AxisAlignedBB.maxZ);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY,
				par1AxisAlignedBB.minZ);
		tessellator.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY,
				par1AxisAlignedBB.minZ);
		tessellator.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY,
				par1AxisAlignedBB.minZ);
		tessellator.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY,
				par1AxisAlignedBB.minZ);
		tessellator.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY,
				par1AxisAlignedBB.maxZ);
		tessellator.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY,
				par1AxisAlignedBB.maxZ);
		tessellator.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY,
				par1AxisAlignedBB.maxZ);
		tessellator.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY,
				par1AxisAlignedBB.maxZ);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY,
				par1AxisAlignedBB.minZ);
		tessellator.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY,
				par1AxisAlignedBB.minZ);
		tessellator.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY,
				par1AxisAlignedBB.maxZ);
		tessellator.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY,
				par1AxisAlignedBB.maxZ);
		tessellator.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY,
				par1AxisAlignedBB.minZ);
		tessellator.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY,
				par1AxisAlignedBB.maxZ);
		tessellator.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY,
				par1AxisAlignedBB.maxZ);
		tessellator.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY,
				par1AxisAlignedBB.minZ);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY,
				par1AxisAlignedBB.minZ);
		tessellator.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY,
				par1AxisAlignedBB.minZ);
		tessellator.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY,
				par1AxisAlignedBB.maxZ);
		tessellator.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY,
				par1AxisAlignedBB.maxZ);
		tessellator.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY,
				par1AxisAlignedBB.minZ);
		tessellator.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY,
				par1AxisAlignedBB.maxZ);
		tessellator.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY,
				par1AxisAlignedBB.maxZ);
		tessellator.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY,
				par1AxisAlignedBB.minZ);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY,
				par1AxisAlignedBB.minZ);
		tessellator.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY,
				par1AxisAlignedBB.minZ);
		tessellator.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY,
				par1AxisAlignedBB.maxZ);
		tessellator.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY,
				par1AxisAlignedBB.maxZ);
		tessellator.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY,
				par1AxisAlignedBB.maxZ);
		tessellator.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY,
				par1AxisAlignedBB.maxZ);
		tessellator.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY,
				par1AxisAlignedBB.minZ);
		tessellator.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY,
				par1AxisAlignedBB.minZ);
		tessellator.draw();
		tessellator.startDrawingQuads();
		tessellator.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY,
				par1AxisAlignedBB.maxZ);
		tessellator.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY,
				par1AxisAlignedBB.maxZ);
		tessellator.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY,
				par1AxisAlignedBB.minZ);
		tessellator.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY,
				par1AxisAlignedBB.minZ);
		tessellator.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY,
				par1AxisAlignedBB.minZ);
		tessellator.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY,
				par1AxisAlignedBB.minZ);
		tessellator.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY,
				par1AxisAlignedBB.maxZ);
		tessellator.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY,
				par1AxisAlignedBB.maxZ);
		tessellator.draw();
	}

	public static void drawLines(AxisAlignedBB par1AxisAlignedBB) {
		GL11.glPushMatrix();
		GL11.glBegin(2);
		GL11.glVertex3d(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY,
				par1AxisAlignedBB.minZ);
		GL11.glVertex3d(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY,
				par1AxisAlignedBB.maxZ);
		GL11.glEnd();
		GL11.glBegin(2);
		GL11.glVertex3d(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY,
				par1AxisAlignedBB.minZ);
		GL11.glVertex3d(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY,
				par1AxisAlignedBB.maxZ);
		GL11.glEnd();
		GL11.glBegin(2);
		GL11.glVertex3d(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY,
				par1AxisAlignedBB.maxZ);
		GL11.glVertex3d(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY,
				par1AxisAlignedBB.maxZ);
		GL11.glEnd();
		GL11.glBegin(2);
		GL11.glVertex3d(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY,
				par1AxisAlignedBB.minZ);
		GL11.glVertex3d(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY,
				par1AxisAlignedBB.minZ);
		GL11.glEnd();
		GL11.glBegin(2);
		GL11.glVertex3d(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY,
				par1AxisAlignedBB.minZ);
		GL11.glVertex3d(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY,
				par1AxisAlignedBB.maxZ);
		GL11.glEnd();
		GL11.glBegin(2);
		GL11.glVertex3d(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY,
				par1AxisAlignedBB.minZ);
		GL11.glVertex3d(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY,
				par1AxisAlignedBB.maxZ);
		GL11.glEnd();
		GL11.glPopMatrix();
	}

	/*
	 * the rest of these methods aren't mine / minecrafts
	 */

	public static void drawOutlinedBoundingBox(AxisAlignedBB par1AxisAlignedBB) {
		final WorldRenderer var2 = Tessellator.instance.getWorldRenderer();
		var2.startDrawing(3);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY,
				par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY,
				par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY,
				par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY,
				par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY,
				par1AxisAlignedBB.minZ);
		var2.draw();
		var2.startDrawing(3);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY,
				par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY,
				par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY,
				par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY,
				par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY,
				par1AxisAlignedBB.minZ);
		var2.draw();
		var2.startDrawing(1);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY,
				par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY,
				par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY,
				par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY,
				par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY,
				par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY,
				par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY,
				par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY,
				par1AxisAlignedBB.maxZ);
		var2.draw();
	}

	public static void drawRect(final float g, final float h, final float i,
			final float j, final int col1) {
		final float f = (col1 >> 24 & 0xFF) / 255F;
		final float f1 = (col1 >> 16 & 0xFF) / 255F;
		final float f2 = (col1 >> 8 & 0xFF) / 255F;
		final float f3 = (col1 & 0xFF) / 255F;

		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);

		GL11.glPushMatrix();
		GL11.glColor4f(f1, f2, f3, f);
		GL11.glBegin(GL11.GL_QUADS);
		GL11.glVertex2d(i, h);
		GL11.glVertex2d(g, h);
		GL11.glVertex2d(g, j);
		GL11.glVertex2d(i, j);
		GL11.glEnd();
		GL11.glPopMatrix();

		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
	}
}
