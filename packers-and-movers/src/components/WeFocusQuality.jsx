import React from "react";
import GppGoodOutlinedIcon from "@mui/icons-material/GppGoodOutlined";
import LocalShippingOutlinedIcon from "@mui/icons-material/LocalShippingOutlined";
import WarehouseOutlinedIcon from "@mui/icons-material/WarehouseOutlined";

const features = [
  {
    id: 1,
    label: "Transparent Pricing",
    Icon: GppGoodOutlinedIcon,
  },
  {
    id: 2,
    label: "Fast, Efficient Delivery",
    Icon: LocalShippingOutlinedIcon,
  },
  {
    id: 3,
    label: "Warehouse Storage",
    Icon: WarehouseOutlinedIcon,
  },
];

const WeFocusQuality = () => {
  return (
    <section className="relative bg-gray-900 py-16 md:py-24 md:pb-64">
      {/* Background Image */}
      <div
        className="absolute inset-0 bg-cover bg-center bg-no-repeat opacity-40"
        style={{ backgroundImage: "url('images/we-focus-quality.png')" }}
      />

      {/* Content */}
      <div className="relative z-10 max-w-7xl mx-auto px-6 md:px-12">
        <div className="flex flex-col lg:flex-row items-center gap-12">
          {/* Left Content */}
          <div className="text-white text-center lg:text-left max-w-xl 2xl:pb-10.5">
            <p className="text-sm font-semibold text-yellow-400 uppercase tracking-wider mb-3">
              Focused on Quality
            </p>

            <h2 className="text-3xl sm:text-4xl lg:text-5xl font-extrabold leading-tight mb-4">
              Global Logistics Partner To World’s Famous Brands
            </h2>

            <p className="text-xl sm:text-2xl font-bold text-yellow-400">
              From Over 25 Years!
            </p>
          </div>

          {/* Right Icons */}
          <div className="flex flex-wrap justify-center gap-10">
            {features?.map(({ id, Icon, label }) => (
              <div
                key={id}
                className="flex flex-col items-center text-center w-[130px] group"
              >
                <Icon
                  sx={{ fontSize: 64 }}
                  className="text-yellow-400 mb-3 transition-transform duration-300 group-hover:-translate-y-1"
                />

                <p className="text-xs uppercase tracking-wider text-white font-medium">
                  {label}
                </p>
              </div>
            ))}
          </div>
        </div>
      </div>
    </section>
  );
};

export default WeFocusQuality;
