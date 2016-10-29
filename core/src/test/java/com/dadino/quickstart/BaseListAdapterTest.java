package com.dadino.quickstart;


import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.dadino.quickstart.core.adapters.BaseListAdapter;
import com.dadino.quickstart.core.adapters.holders.BaseHolder;

import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;

public class BaseListAdapterTest {

	@Test
	public void heaters3_footers3_allGood() throws
			Exception {
		BaseListAdapter adapter = getAdapter(3, 3);

		List<TestObject> objects = new ArrayList<>();
		objects.add(new TestObject(1));
		objects.add(new TestObject(2));
		objects.add(new TestObject(3));
		adapter.setItems(objects);
		//Item count
		assertEquals(adapter.getItemCount(), 9);

		//Get item
		assertNull(adapter.getItem(0));
		assertNull(adapter.getItem(1));
		assertNull(adapter.getItem(2));
		assertNotNull(adapter.getItem(3));
		assertNotNull(adapter.getItem(4));
		assertNotNull(adapter.getItem(5));
		assertNull(adapter.getItem(6));
		assertNull(adapter.getItem(7));
		assertNull(adapter.getItem(8));

		//Get item id
		assertEquals(1, adapter.getItemId(3));
		assertEquals(2, adapter.getItemId(4));
		assertEquals(3, adapter.getItemId(5));

		//Get position
		assertEquals(3, adapter.getPosition(1));
		assertEquals(4, adapter.getPosition(2));
		assertEquals(5, adapter.getPosition(3));
	}

	@Test
	public void heaters3_footers0_allGood() throws
			Exception {
		BaseListAdapter adapter = getAdapter(3, 0);

		List<TestObject> objects = new ArrayList<>();
		objects.add(new TestObject(1));
		objects.add(new TestObject(2));
		objects.add(new TestObject(3));
		adapter.setItems(objects);
		//Item count
		assertEquals(adapter.getItemCount(), 6);

		//Get item
		assertNull(adapter.getItem(0));
		assertNull(adapter.getItem(1));
		assertNull(adapter.getItem(2));
		assertNotNull(adapter.getItem(3));
		assertNotNull(adapter.getItem(4));
		assertNotNull(adapter.getItem(5));

		//Get item id
		assertEquals(1, adapter.getItemId(3));
		assertEquals(2, adapter.getItemId(4));
		assertEquals(3, adapter.getItemId(5));

		//Get position
		assertEquals(3, adapter.getPosition(1));
		assertEquals(4, adapter.getPosition(2));
		assertEquals(5, adapter.getPosition(3));
	}

	@Test
	public void heaters0_footers3_allGood() throws
			Exception {
		BaseListAdapter adapter = getAdapter(0, 3);

		List<TestObject> objects = new ArrayList<>();
		objects.add(new TestObject(1));
		objects.add(new TestObject(2));
		objects.add(new TestObject(3));
		adapter.setItems(objects);
		//Item count
		assertEquals(adapter.getItemCount(), 6);

		//Get item
		assertNotNull(adapter.getItem(0));
		assertNotNull(adapter.getItem(1));
		assertNotNull(adapter.getItem(2));
		assertNull(adapter.getItem(3));
		assertNull(adapter.getItem(4));
		assertNull(adapter.getItem(5));

		//Get item id
		assertEquals(1, adapter.getItemId(0));
		assertEquals(2, adapter.getItemId(1));
		assertEquals(3, adapter.getItemId(2));

		//Get position
		assertEquals(0, adapter.getPosition(1));
		assertEquals(1, adapter.getPosition(2));
		assertEquals(2, adapter.getPosition(3));
	}

	@Test
	public void heaters0_footers0_allGood() throws
			Exception {
		BaseListAdapter adapter = getAdapter(0, 0);

		List<TestObject> objects = new ArrayList<>();
		objects.add(new TestObject(1));
		objects.add(new TestObject(2));
		objects.add(new TestObject(3));
		adapter.setItems(objects);
		//Item count
		assertEquals(adapter.getItemCount(), 3);

		//Get item
		assertNotNull(adapter.getItem(0));
		assertNotNull(adapter.getItem(1));
		assertNotNull(adapter.getItem(2));

		//Get item id
		assertEquals(1, adapter.getItemId(0));
		assertEquals(2, adapter.getItemId(1));
		assertEquals(3, adapter.getItemId(2));

		//Get position
		assertEquals(0, adapter.getPosition(1));
		assertEquals(1, adapter.getPosition(2));
		assertEquals(2, adapter.getPosition(3));
	}

	@NonNull
	private BaseListAdapter getAdapter(int headers, int footers) {
		return new BaseListAdapter() {

			@Override
			public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

			}

			@Override
			public void setHasStableIds(boolean hasStableIds) {

			}

			@Override
			protected boolean useStableId() {
				return false;
			}

			@Override
			public long getItemIdSafe(int position) {
				return ((TestObject) getItem(position)).getId();
			}

			@Override
			public int getFootersCount() {
				return footers;
			}

			@Override
			public int getHeadersCount() {
				return headers;
			}

			@Override
			protected BaseHolder getHolder(ViewGroup parent, int viewType) {
				return null;
			}
		};
	}

	public class TestObject {

		private final long id;

		public TestObject(long id) {this.id = id;}

		public long getId() {
			return id;
		}
	}
}